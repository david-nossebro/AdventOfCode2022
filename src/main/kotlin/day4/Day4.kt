package day4

import utils.readFile
import utils.splitOnNewLine

fun main() {
    val input = readFile("day4.txt")

    println("P1: ${part1(input)}")
    println("P2: ${part2(input)}")
}

fun part1(input: String): Int {
    return input.splitOnNewLine().map { row ->
        row.split(",").map { elfRangeString ->
            elfRangeString.split("-").map { it.toInt() }
        }.map { (areaStart, areaEnd) -> (areaStart..areaEnd) }
    }.count { (firstElfArea, secondElfArea) ->
        firstElfArea containsAllOrIsContainedIn secondElfArea
    }
}

fun part2(input: String): Int {
    return input.splitOnNewLine().map { row ->
        row.split(",").map { elfRangeString ->
            elfRangeString.split("-").map { it.toInt() }
        }.map { (areaStart, areaEnd) -> (areaStart..areaEnd) }
    }.count { (firstElfArea, secondElfArea) -> firstElfArea overlaps secondElfArea }
}

infix fun IntRange.overlaps(otherRange: IntRange) = (this intersect otherRange).isNotEmpty()

infix fun IntRange.containsAllOrIsContainedIn(otherRange: IntRange) =
    otherRange.all { this.contains(it) } || this.all { otherRange.contains(it) }