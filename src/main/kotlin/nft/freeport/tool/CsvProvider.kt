package nft.freeport.tool

import com.univocity.parsers.csv.CsvParser
import com.univocity.parsers.csv.CsvParserSettings
import com.univocity.parsers.csv.CsvWriter
import com.univocity.parsers.csv.CsvWriterSettings
import javax.enterprise.inject.Produces

class CsvProvider {
    @Produces
    fun csvParser(): CsvParser {
        val settings = CsvParserSettings()
        settings.isHeaderExtractionEnabled = true
        return CsvParser(settings)
    }

    @Produces
    fun csvWriter(): CsvWriter {
        return CsvWriter(CsvWriterSettings())
    }
}
