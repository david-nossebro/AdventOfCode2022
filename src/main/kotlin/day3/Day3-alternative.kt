package day3.alternative

import utils.readFile
import utils.splitOnNewLine

fun main() {
    val input = readFile("day3.txt")

    println("P1 - Total prio: ${part1(input)}")
    println("P2: Total prio for badges: ${part2(input)}")
}

fun part1(input: String) =
    input.splitOnNewLine().map { row ->
        row.chunked(row.length / 2)
            .let { (firstCompartment, secondCompartment) ->
                (firstCompartment intersect secondCompartment).single()
            }
    }.sumOf { item -> item.getPriority() }

fun part2(input: String) =
    input.splitOnNewLine().chunked(3).sumOf { (firstBackpack, secondBackpack, thirdBackpack) ->
        (firstBackpack intersect secondBackpack intersect thirdBackpack).single().getPriority()
    }

val itemTypePriority = (('a'..'z') + ('A'..'Z')).zip((1..52)).toMap()
fun Char.getPriority() = itemTypePriority[this] ?: error("Prio not found for item type $this")
infix fun String.intersect(otherString: String) = (this.toSet() intersect otherString.toSet()).joinToString()