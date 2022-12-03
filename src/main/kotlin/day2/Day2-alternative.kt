package day2.alternative

import utils.readFile
import utils.splitOnNewLine

fun main() {
    val input = readFile("day2.txt")

    // Part 1
    input.splitOnNewLine().map { row ->
        row.split(" ").map { gestureString -> gestureString.toGesture() }
    }.sumOf { (opponent, you) -> (you vs opponent).score + you.point
    }.let { println("P1: Total score: $it") }

    // Part 2
    input.splitOnNewLine().map { row ->
        row.split(" ")
    }.sumOf { (opponentString, wantedResultString) ->
        val opponent = opponentString.toGesture()
        val result = wantedResultString.toWantedResult()
        result.score + (opponent gestureAgaistToGet result).point
    }.let { println("P2: Total score: $it") }
}

fun String.toGesture() = Gesture.fromString(this)
fun String.toWantedResult() = Result.fromString(this)

enum class Gesture(val point: Int) {
    ROCK(1),
    PAPER(2),
    SCISSOR(3);

    infix fun vs(otherGesture: Gesture) = when  {
        this == otherGesture -> Result.DRAW
        this beats otherGesture -> Result.WIN
        else -> Result.LOSE
    }

    infix fun gestureAgaistToGet(result: Result) = when(result) {
        Result.DRAW -> this
        Result.WIN -> this.losesTo()
        Result.LOSE -> this.winsAgainst()
    }

    infix fun beats(otherGesture: Gesture) =
        this.winsAgainst() == otherGesture

    fun winsAgainst() = when(this) {
        ROCK -> SCISSOR
        PAPER -> ROCK
        SCISSOR -> PAPER
    }
    fun losesTo() = when(this) {
        ROCK -> PAPER
        PAPER -> SCISSOR
        SCISSOR -> ROCK
    }

    companion object {
        fun fromString(string: String) = when(string) {
            "A", "X" -> ROCK
            "B", "Y" -> PAPER
            "C", "Z" -> SCISSOR
            else -> error("Unable to create gesture from '$string'")
        }
    }
}

enum class Result(val score: Int) {
    LOSE(0),
    DRAW(3),
    WIN(6);

    companion object {
        fun fromString(string: String) = when(string) {
            "X" -> LOSE
            "Y" -> DRAW
            "Z" -> WIN
            else -> error("Unable to create result from '$string'")
        }
    }
}