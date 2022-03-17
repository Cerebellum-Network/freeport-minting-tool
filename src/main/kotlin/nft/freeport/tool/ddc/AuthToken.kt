package nft.freeport.tool.ddc

data class AuthToken(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
)
