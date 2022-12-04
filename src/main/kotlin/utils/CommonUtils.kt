package utils

import java.io.File
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL

val NEW_LINE: String = System.lineSeparator()
val BLANC_LINE = NEW_LINE + NEW_LINE

fun String.splitOnNewLine() = split(NEW_LINE)
fun String.splitOnBlancLine() = split(BLANC_LINE)
fun String.splitToInt(vararg delimiter: String) = this.split(*delimiter).map { it.toInt() }
fun String.group(groupDelimiter: String = BLANC_LINE, itemDelimiter: String = NEW_LINE) =
    split(groupDelimiter).map { it.split(itemDelimiter) }
fun List<String>.sum() = sumOf { it.toBigDecimal() }
@JvmName("sumBigDecimal")
fun List<BigDecimal>.sum() = sumOf { it }
fun String.binaryToInt() = Integer.parseInt(this, 2)
infix fun CharSequence.intersect(otherString: CharSequence) = (this.toSet() intersect otherString.toSet()).joinToString()
infix fun CharSequence.overlaps(otherString: CharSequence) = (this intersect otherString).isNotEmpty()
infix fun String.containsAllOrIsContainedIn(otherString: String) =
    otherString.all { this.contains(it) } || this.all { otherString.contains(it) }
infix fun <T: Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>) =
    start <= other.endInclusive && endInclusive >= other.start

infix fun <T: Comparable<T>> ClosedRange<T>.containsAllOrIsContainedIn(other: ClosedRange<T>) =
    this containsAll other || other containsAll this

infix fun <T: Comparable<T>> ClosedRange<T>.containsAll(otherRange: ClosedRange<T>) =
    start <= otherRange.start && endInclusive >= otherRange.endInclusive

infix fun <T> Collection<T>.overlaps(other: Collection<T>) =
    any { it in other }

infix fun <T> Collection<T>.containsAllOrIsContainedIn(other: Collection<T>) =
    this containsAll other || other containsAll this

infix fun <T> Collection<T>.containsAll(other: Collection<T>) =
    this.all { it in other }

fun readFile(fileName: String)
        = File("src/main/resources/inputs/$fileName").inputStream().readBytes().toString(Charsets.UTF_8)

fun readUrl(url: String) = sendRequest(url).body

/**
 * Sends an HTTP request to the given [url], using the given HTTP [method]. The request can also
 * include custom [headers] and [body].
 *
 * Returns the [Response] object containing [statusCode][Response.statusCode],
 * [headers][Response.headers] and [body][Response.body].
 */
fun sendRequest(url: String, method: String = "GET", headers: Map<String, String>? = null, body: String? = null): Response {
    val conn = URL(url).openConnection() as HttpURLConnection

    with(conn) {
        requestMethod = method
        doOutput = body != null
        headers?.forEach(this::setRequestProperty)
    }

    if (body != null) {
        conn.outputStream.use {
            it.write(body.toByteArray())
        }
    }

    val responseBody = conn.inputStream.use { it.readBytes() }.toString(Charsets.UTF_8)

    return Response(conn.responseCode, conn.headerFields, responseBody)
}

data class Response(val statusCode: Int, val headers: Map<String, List<String>>? = null, val body: String? = null)