package nft.freeport.tool

import com.univocity.parsers.csv.CsvParser
import nft.freeport.tool.state.State
import java.nio.file.Paths
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.readLines

@Path("/preview")
class PreviewResource(private val csvParser: CsvParser) {
    @GET
    fun preview(@QueryParam("path") path: String, @QueryParam("env") env: Env): State {
        State.env = env
        State.path = path

        val dataDir = Paths.get(path)
        require(dataDir.exists() && dataDir.isDirectory()) { "Data dir not exists or not a directory" }

        val keyFile = dataDir.resolve(KEY_FILE_NAME)
        require(keyFile.exists() && keyFile.isRegularFile()) { "Key file not found" }

        val key = keyFile.readLines().first()

        State.key = key

        val csvFile = dataDir.resolve(CSV_FILE_NAME)
        require(csvFile.exists() && csvFile.isRegularFile()) { "CSV file not found" }

        val nfts = csvParser.parseAll(csvFile.toFile(), Charsets.UTF_8.name())
            .map {
                Nft(
                    name = it[0],
                    description = it[1],
                    supply = it[2].toInt(),
                    asset = it[3],
                    preview = it[4],
                    price = it[5].toInt(),
                    ja = it[6],
                    primaryCut = it[7].toInt(),
                    secondaryCut = it[8].toInt(),
                )
            }
        State.nfts = nfts

        val assetsFolder = dataDir.resolve(ASSETS_FOLDER)
        require(assetsFolder.exists() && assetsFolder.isDirectory()) { "Assets folder not found" }

        val previewsFolder = dataDir.resolve(PREVIEWS_FOLDER)
        require(previewsFolder.exists() && previewsFolder.isDirectory()) { "Previews folder not found" }

        nfts.forEach {
            val assetFile = assetsFolder.resolve(it.asset)
            require(assetFile.exists() && assetFile.isRegularFile()) { "Asset file ${it.asset} not found" }

            val previewFile = previewsFolder.resolve(it.preview)
            require(previewFile.exists() && previewFile.isRegularFile()) { "Preview file ${it.preview} not found" }
        }

        return State
    }
}
