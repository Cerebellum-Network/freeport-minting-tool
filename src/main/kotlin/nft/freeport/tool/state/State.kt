package nft.freeport.tool.state

import nft.freeport.tool.Env
import nft.freeport.tool.Nft

object State {
    var path: String = ""

    var nfts: List<Nft> = emptyList()

    var key: String = ""

    var env: Env = Env.DEV

    var progress: List<NftProgress> = emptyList()

    var running: Boolean = false

    var error: String? = null

    fun initProgress() {
        running = true
        error = null
        progress = nfts.map { NftProgress(it.name) }
    }
}
