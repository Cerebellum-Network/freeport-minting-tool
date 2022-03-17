package nft.freeport.tool

import com.univocity.parsers.csv.CsvWriter
import nft.freeport.tool.state.State
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/mint")
class MintingResource(private val mintingService: MintingService, private val csvWriter: CsvWriter) {
    @GET
    fun mint() {
        mintingService.mint()
    }

    @GET
    @Path("/state")
    fun progress() = State

    @GET
    @Path("/report.csv")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    fun report(): ByteArray {
        val builder = StringBuilder()
        builder.appendLine(
            csvWriter.writeRowToString(
                listOf(
                    "Name",
                    "Mint tx",
                    "NFT ID",
                    "Price tx",
                    "Royalties tx",
                    "DDC CID",
                    "Attachment tx"
                )
            )
        )
        State.progress.asSequence()
            .map {
                csvWriter.writeRowToString(
                    listOf(
                        it.name,
                        it.mintingTxHash,
                        it.nftId,
                        it.priceTxHash,
                        it.royaltyTxHash,
                        it.ddcCid,
                        it.attachmentTxHash
                    )
                )
            }.forEach(builder::appendLine)
        return builder.toString().toByteArray()
    }
}
