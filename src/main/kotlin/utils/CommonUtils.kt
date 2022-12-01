package utils

import java.io.File

val NEW_LINE: String = System.lineSeparator()
val BLANC_LINE = NEW_LINE + NEW_LINE

fun String.splitOnNewLine() = split(NEW_LINE)
fun String.splitOnBlancLine() = split(BLANC_LINE)
fun List<String>.sum() = sumOf { it.toInt() }

fun readFile(fileName: String)
        = File("src/main/resources/inputs/$fileName").inputStream().readBytes().toString(Charsets.UTF_8)
