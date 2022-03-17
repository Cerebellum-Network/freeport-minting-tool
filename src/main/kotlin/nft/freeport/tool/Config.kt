package nft.freeport.tool

import java.net.URI

data class Config(
    val rpc: String,
    val chainId: Long,
    val freeportContract: String,
    val attachmentContract: String,
    val ddcProxyUrl: URI,
)
