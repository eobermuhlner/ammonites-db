package ch.obermuhlner.ammonites.imports.parser

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class CsvParser : ImportParser {
    override fun parse(inputStream: InputStream, processHeader: (List<String>) -> String, processRow: (List<String>) -> String): String {
        val result = StringBuilder()
        val bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        var rowCount = 0
        bufferedReader.useLines { lines ->
            lines.filter { line -> !line.startsWith("#") }
                .forEach { line ->
                    println("LINE: $line")
                    val parts = parseCsvLine(line)
                    if (rowCount == 0) {
                        result.append(processHeader(parts)).append("\n")
                    } else {
                        result.append(processRow(parts)).append("\n")
                    }
                    rowCount++
                }
        }
        return result.toString()
    }

    private fun parseCsvLine(line: String): List<String> {
        val regex = """,(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"""
        return line.split(regex.toRegex()).map { it.trim(' ', '"') }
    }
}
