package nft.freeport.tool.ddc

data class AuthRequest(
    val encryptionPublicKey: String,
    val signature: String,
)
