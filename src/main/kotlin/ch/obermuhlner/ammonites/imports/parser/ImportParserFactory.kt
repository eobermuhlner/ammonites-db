package ch.obermuhlner.ammonites.imports.parser

import org.springframework.web.multipart.MultipartFile

class ImportParserFactory {
    fun getParser(file: MultipartFile): ImportParser {
        return when {
            file.contentType.equals("application/vnd.ms-excel", ignoreCase = true) -> ExcelParser()
            file.contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ignoreCase = true) -> ExcelParser()
            file.contentType.equals("text/csv", ignoreCase = true) -> CsvParser()
            file.originalFilename.endsWith(".xlsx") -> ExcelParser()
            file.originalFilename.endsWith(".xls") -> ExcelParser()
            file.originalFilename.endsWith(".csv") -> CsvParser()
            else -> throw IllegalArgumentException("Unsupported file type: ${file.contentType} ${file.originalFilename}")
        }
    }
}
