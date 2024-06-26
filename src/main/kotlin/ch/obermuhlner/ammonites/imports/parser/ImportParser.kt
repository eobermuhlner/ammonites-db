package ch.obermuhlner.ammonites.imports.parser

import java.io.InputStream

interface ImportParser {
    fun parse(
        inputStream: InputStream,
        log: StringBuilder,
        processHeader: (row: List<String>, log: StringBuilder) -> Unit,
        processRow: (row: List<String>, log: StringBuilder) -> Unit
    ): String
}
