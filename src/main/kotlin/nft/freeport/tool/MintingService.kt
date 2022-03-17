package nft.freeport.tool

import com.codahale.xsalsa20poly1305.Keys
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.withIndex
import nft.freeport.contract.Freeport
import nft.freeport.contract.NFTAttachment
import nft.freeport.tool.ddc.AuthRequest
import nft.freeport.tool.ddc.DdcProxy
import nft.freeport.tool.ddc.LargeAssetUploadForm
import nft.freeport.tool.state.State
import okio.ByteString.Companion.decodeHex
import org.eclipse.microprofile.rest.client.RestClientBuilder
import org.kethereum.crypto.signMessage
import org.kethereum.crypto.toECKeyPair
import org.kethereum.crypto.toHex
import org.kethereum.model.PrivateKey
import org.kethereum.model.SignatureData
import org.komputing.khex.extensions.hexToByteArray
import org.komputing.khex.model.HexString
import org.slf4j.LoggerFactory
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.FastRawTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.Executors
import javax.activation.MimetypesFileTypeMap
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.HttpMethod

@ApplicationScoped
class MintingService {
    private val log = LoggerFactory.getLogger(javaClass)

    private val asyncPool = Executors.newSingleThreadExecutor()
    private val txExecutorPool = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
    private val uploadExecutorPool = Executors.newFixedThreadPool(2).asCoroutineDispatcher()

    private val configsMap = mapOf(
        Env.DEV to Config(
            rpc = "https://rpc-mumbai.matic.today",
            chainId = 80001,
            freeportContract = "0x702BA959B5542B2Bf88a1C5924F73Ed97482c64B",
            attachmentContract = "0x39B27a0bc81C1366E2b05E02642Ef343a4f9223a",
            ddcProxyUrl = URI.create("https://ddc.freeport.dev.cere.network")
        ),
        Env.STG to Config(
            rpc = "https://rpc-mumbai.matic.today",
            chainId = 80001,
            freeportContract = "0xacd8105cBA046307d2228794ba2F81aA15e82E0D",
            attachmentContract = "0x84766787c6b9131927A76634F7DDCfcf3ff2e9d1",
            ddcProxyUrl = URI.create("https://ddc.freeport.stg.cere.network")
        ),
        Env.PROD to Config(
            rpc = "https://polygon-rpc.com",
            chainId = 137,
            freeportContract = "0xf9AC6022F786f6f64Fd8abf661190b8517D92396",
            attachmentContract = "0x651f2C6942F1c290632Ad5bB61D9ece789f82f35",
            ddcProxyUrl = URI.create("https://ddc.freeport.cere.network")
        )
    )

    private val web3Map = configsMap.mapValues { (_, config) ->
        Web3j.build(HttpService(config.rpc))
    }

    private val ddcProxyMap = configsMap.mapValues { (_, config) ->
        RestClientBuilder.newBuilder().baseUri(config.ddcProxyUrl).build(DdcProxy::class.java)
    }

    private val gasProviders = mapOf(
        Env.DEV to StaticGasProvider(BigInteger.valueOf(25_000_000_000L), BigInteger.valueOf(9_000_000L)),
        Env.STG to StaticGasProvider(BigInteger.valueOf(25_000_000_000L), BigInteger.valueOf(9_000_000L)),
        Env.PROD to StaticGasProvider(BigInteger.valueOf(50_000_000_000L), BigInteger.valueOf(9_000_000L)),
    )

    fun mint() {
        if (State.running) {
            throw IllegalStateException("Minting is already running")
        }
        State.initProgress()
        asyncPool.submit { mintCatching() }
    }

    private fun mintCatching() {
        runCatching {
            mintInternal()
        }.onFailure {
            log.error("Minting failed", it)
            State.error = it.message
        }
        State.running = false
    }

    private fun mintInternal() {
        val credentials = Credentials.create(State.key)
        val ecKeyPair = HexString(State.key)
            .hexToByteArray()
            .let(::PrivateKey)
            .let(PrivateKey::toECKeyPair)
        val minterAddress = credentials.address
        val encryptionKey = State.key.decodeHex().toByteArray()
            .let(Keys::generatePublicKey)
            .let { Base64.getEncoder().encodeToString(it) }
        val basePath = Paths.get(State.path)
        val config = configsMap.getValue(State.env)
        val web3 = web3Map.getValue(State.env)
        val txManager = FastRawTransactionManager(web3, credentials, config.chainId)
        val gasProvider = gasProviders.getValue(State.env)
        val freeport = Freeport.load(config.freeportContract, web3, txManager, gasProvider)
        val nftAttachment = NFTAttachment.load(config.attachmentContract, web3, txManager, gasProvider)
        val ddcProxy = ddcProxyMap.getValue(State.env)
        val ddcProxyNonce = ddcProxy.getNonce(minterAddress)
        val ddcAuthMsg = "$minterAddress$encryptionKey$ddcProxyNonce"
        val signature = "$MSG_PREFIX${ddcAuthMsg.length}$ddcAuthMsg".toByteArray()
            .let(ecKeyPair::signMessage)
            .let(SignatureData::toHex)
        val authToken = ddcProxy.authenticate(minterAddress, AuthRequest(encryptionKey, signature))
        val authHeader = "${authToken.tokenType} ${authToken.accessToken}"

        log.info("Starting minting tool with profile ${State.env}")
        log.info("Minter address: $minterAddress")

        runBlocking {
            State.nfts.asFlow()
                .withIndex()
                .map { async(txExecutorPool) { mint(it, freeport, nftAttachment, ddcProxy, authHeader, basePath) } }
                .toList()
                .awaitAll()
        }
    }

    private suspend fun mint(
        nft: IndexedValue<Nft>,
        freeport: Freeport,
        nftAttachment: NFTAttachment,
        ddcProxy: DdcProxy,
        authHeader: String,
        basePath: Path
    ) = coroutineScope {
        val issueDef = async(txExecutorPool) { issue(nft, freeport) }
        val uploadDef = async(uploadExecutorPool) { uploadAsset(nft, ddcProxy, authHeader, basePath) }
        awaitAll(issueDef, uploadDef)
        val attachmentDef = async(txExecutorPool) { attach(nft.index, nftAttachment) }
        val priceDef = async(txExecutorPool) { setPrice(nft, freeport) }
        val royaltiesDef = async(txExecutorPool) { configureRoyalties(nft, freeport) }
        awaitAll(attachmentDef, priceDef, royaltiesDef)
    }

    private fun issue(nft: IndexedValue<Nft>, freeport: Freeport) {
        log.info("Issue NFT #${nft.index}")
        val mintingReceipt = freeport.issue(nft.value.supply.toBigInteger(), byteArrayOf()).send()
        val nftId = freeport.getTransferSingleEvents(mintingReceipt).first().id
        log.info("Issue NFT #${nft.index} SUCCESS")
        with(State.progress[nft.index]) {
            minted = true
            mintingTxHash = mintingReceipt.transactionHash
            this.nftId = nftId.toString()
        }
    }

    private fun uploadAsset(nft: IndexedValue<Nft>, ddcProxy: DdcProxy, authHeader: String, basePath: Path) {
        log.info("Uploading asset for NFT #${nft.index}")
        val presignedUrls = ddcProxy.generatePutPresignedUrls(authHeader)
        val asset = basePath.resolve(ASSETS_FOLDER).resolve(nft.value.asset)
        val preview = basePath.resolve(PREVIEWS_FOLDER).resolve(nft.value.preview)
        uploadFile(presignedUrls.assetUrl, asset)
        uploadFile(presignedUrls.previewUrl, preview)
        val cid = ddcProxy.upload(
            presignedUrls.assetKey, LargeAssetUploadForm(
                title = nft.value.name,
                description = nft.value.description,
                contentType = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(asset.toFile())
            ), authHeader
        )
        log.info("Uploading asset for NFT #${nft.index} SUCCESS")
        with(State.progress[nft.index]) {
            assetUploaded = true
            ddcCid = cid
        }
    }

    private fun uploadFile(url: String, file: Path) {
        log.info("Uploading file $file to presigned url $url")
        with(URL(url).openConnection() as HttpURLConnection) {
            doOutput = true
            requestMethod = HttpMethod.PUT
            outputStream.use {
                Files.copy(file, it)
            }
            log.info("Response code: $responseCode")
        }
    }

    private fun attach(i: Int, nftAttachment: NFTAttachment) {
        val nft = State.progress[i]
        val cid = requireNotNull(nft.ddcCid)
        val nftId = requireNotNull(nft.nftId)
        log.info("Attaching CID $cid to NFT $nftId")
        val receipt = nftAttachment.minterAttachToNFT(nftId.toBigInteger(), cid.toByteArray()).send()
        log.info("Attaching CID $cid to NFT $nftId SUCCESS")
        State.progress[i].attachmentTxHash = receipt.transactionHash
    }

    private fun setPrice(nft: IndexedValue<Nft>, freeport: Freeport) {
        val nftId = requireNotNull(State.progress[nft.index].nftId)
        log.info("Configuring price for NFT $nftId")
        val receipt =
            freeport.makeOffer(nftId.toBigInteger(), (PRICE_MULTIPLIER * nft.value.price).toBigInteger()).send()
        log.info("Configuring price for NFT $nftId SUCCESS")
        with(State.progress[nft.index]) {
            priceSet = true
            priceTxHash = receipt.transactionHash
        }
    }

    private fun configureRoyalties(nft: IndexedValue<Nft>, freeport: Freeport) {
        val nftId = requireNotNull(State.progress[nft.index].nftId)
        log.info("Configuring royalties for NFT $nftId")
        val receipt = freeport.configureRoyalties(
            nftId.toBigInteger(),
            nft.value.ja,
            (nft.value.primaryCut * PERCENT_MULTIPLIER).toBigInteger(),
            BigInteger.ZERO,
            nft.value.ja,
            (nft.value.secondaryCut * PERCENT_MULTIPLIER).toBigInteger(),
            BigInteger.ZERO
        ).send()
        log.info("Configuring royalties for NFT $nftId SUCCESS")
        with(State.progress[nft.index]) {
            royaltySet = true
            royaltyTxHash = receipt.transactionHash
        }
    }
}
