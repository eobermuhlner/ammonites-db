package ch.obermuhlner.ammonites.imports.parser

import java.io.InputStream

interface ImportParser {
    fun parse(inputStream: InputStream, processHeader: (List<String>) -> String, processRow: (List<String>) -> String): String
}
