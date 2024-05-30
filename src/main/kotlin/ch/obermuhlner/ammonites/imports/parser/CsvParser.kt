package ch.obermuhlner.ammonites.imports.parser

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class CsvParser : ImportParser {
    override fun parse(
        inputStream: InputStream,
        log: StringBuilder,
        processHeader: (row: List<String>, log: StringBuilder) -> Unit,
        processRow: (row: List<String>, log: StringBuilder) -> Unit
    ): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        var rowCount = 0
        bufferedReader.useLines { lines ->
            lines.filter { line -> !line.startsWith("#") }
                .forEach { line ->
                    val parts = parseCsvLine(line)
                    if (rowCount == 0) {
                        processHeader(parts, log)
                    } else {
                        processRow(parts, log)
                    }
                    rowCount++
                }
        }
        return log.toString()
    }

    private fun parseCsvLine(line: String): List<String> {
        val regex = """,(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"""
        return line.split(regex.toRegex()).map { it.trim(' ', '"') }
    }
}
