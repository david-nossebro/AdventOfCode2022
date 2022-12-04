package day4.alternative

import utils.readFile
import utils.splitOnNewLine

fun main() {
    val inputRows = readFile("day4.txt").splitOnNewLine()

    println("P1: ${part1(inputRows)}")
    println("P2: ${part2(inputRows)}")
}

fun part1(inputRows: List<String>) =
    inputRows.toCleaningAreaPairs().count { (firstArea, secondArea) ->
        firstArea containsAllOrIsContainedIn secondArea
    }

fun part2(inputRows: List<String>) =
    inputRows.toCleaningAreaPairs().count { (firstArea, secondArea) ->
        firstArea overlaps secondArea
    }

fun List<String>.toCleaningAreaPairs() =
    map { row -> row.split(",").let { (firstArea, secondArea) ->
            firstArea.toIntRange() to secondArea.toIntRange()
        }
    }

fun String.toIntRange() = this.splitToInt("-")
    .let { (areaStart, areaEnd) -> (areaStart..areaEnd) }

fun String.splitToInt(delimiter: String) = this.split(delimiter).map { it.toInt() }

infix fun IntRange.overlaps(otherRange: IntRange) = (this intersect otherRange).isNotEmpty()

infix fun IntRange.containsAllOrIsContainedIn(otherRange: IntRange) =
    otherRange.all { this.contains(it) } || this.all { otherRange.contains(it) }
