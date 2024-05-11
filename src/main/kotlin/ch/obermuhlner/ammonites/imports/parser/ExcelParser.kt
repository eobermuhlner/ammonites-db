package ch.obermuhlner.ammonites.imports.parser

import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

class ExcelParser : ImportParser {
    override fun parse(inputStream: InputStream, processHeader: (List<String>) -> String, processRow: (List<String>) -> String): String {
        val result = StringBuilder()
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        sheet.forEach { row ->
            val cells = row.map { it.toString().trim() }
            if (row.rowNum == 0) {
                result.append(processHeader(cells)).append("\n")
            } else {
                result.append(processRow(cells)).append("\n")
            }
        }
        workbook.close()
        return result.toString()
    }
}
