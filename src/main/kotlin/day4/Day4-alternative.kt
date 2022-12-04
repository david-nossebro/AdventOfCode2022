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
    map { row -> row.splitToInt(",", "-").let { (firstStart, firstEnd, secondStart, secondEnd) ->
            firstStart..firstEnd to secondStart..secondEnd
        }
    }

fun String.splitToInt(vararg delimiter: String) = this.split(*delimiter).map { it.toInt() }

infix fun <T: Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>) =
    start <= other.endInclusive && endInclusive >= other.start

infix fun <T: Comparable<T>> ClosedRange<T>.containsAllOrIsContainedIn(other: ClosedRange<T>) =
    this containsAll other || other containsAll this

infix fun <T: Comparable<T>> ClosedRange<T>.containsAll(otherRange: ClosedRange<T>) =
    start <= otherRange.start && endInclusive >= otherRange.endInclusive