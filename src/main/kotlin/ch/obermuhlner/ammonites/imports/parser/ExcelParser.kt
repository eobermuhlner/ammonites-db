package ch.obermuhlner.ammonites.imports.parser

import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream

class ExcelParser : ImportParser {
    override fun parse(
        inputStream: InputStream,
        processHeader: (row: List<String>, log: StringBuilder) -> Unit,
        processRow: (row: List<String>, log: StringBuilder) -> Unit
    ): String {
        val log = StringBuilder()
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        sheet.forEach { row ->
            val cells = row.map { it.toString().trim() }
            if (row.rowNum == 0) {
                processHeader(cells, log)
            } else {
                processRow(cells, log)
            }
        }
        workbook.close()
        return log.toString()
    }
}
