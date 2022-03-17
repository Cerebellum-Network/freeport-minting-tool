package nft.freeport.tool.state

data class NftProgress(
    val name: String,
    var minted: Boolean = false,
    var mintingTxHash: String? = null,
    var nftId: String? = null,
    var priceSet: Boolean = false,
    var priceTxHash: String? = null,
    var royaltySet: Boolean = false,
    var royaltyTxHash: String? = null,
    var assetUploaded: Boolean = false,
    var ddcCid: String? = null,
    var attachmentTxHash: String? = null,
)
