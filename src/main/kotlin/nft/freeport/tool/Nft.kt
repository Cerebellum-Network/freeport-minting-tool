package nft.freeport.tool

data class Nft(
    val name: String,
    val description: String,
    val supply: Int,
    val asset: String,
    val preview: String,
    val price: Int,
    val ja: String,
    val primaryCut: Int,
    val secondaryCut: Int,
)
