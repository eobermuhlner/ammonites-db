package ch.obermuhlner.ammonites.imports.parser

class ImportParserFactory {
    fun getParser(contentType: String?): ImportParser {
        return when {
            contentType.equals("application/vnd.ms-excel", ignoreCase = true) -> ExcelParser()
            contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ignoreCase = true) -> ExcelParser()
            contentType.equals("text/csv", ignoreCase = true) -> CsvParser()
            else -> throw IllegalArgumentException("Unsupported file type")
        }
    }
}
