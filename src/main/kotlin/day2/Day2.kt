package day2

import utils.readFile
import utils.splitOnNewLine

fun main() {
    val input = readFile("day2.txt")

    // Part 1
    input.splitOnNewLine().sumOf(::calculateScoreP1)
        .let { println("Total score P1: $it") }

    // Part 2
    input.splitOnNewLine().sumOf(::calculateScoreP2)
        .let { println("Total score P2: $it") }
}

fun calculateScoreP1(roundString: String): Int {
    val opponent = Play.fromString(roundString.split(" ")[0])
    val you = Play.fromString(roundString.split(" ")[1])
    return you.against(opponent).points + you.pointsFromChoosing
}

fun calculateScoreP2(roundString: String): Int {
    val opponent = Play.fromString(roundString.split(" ")[0])
    val neededResult = Result.fromString(roundString.split(" ")[1])
    val yourPlay = neededResult.getNeededPlay(opponent)
    return neededResult.points + yourPlay.pointsFromChoosing
}
enum class Play(
    val pointsFromChoosing: Int
) {
    ROCK(pointsFromChoosing = 1),
    PAPER(pointsFromChoosing = 2),
    SCISSOR(pointsFromChoosing = 3);

    fun against(otherPlay: Play) =
        when {
            isWin(otherPlay) -> Result.WIN
            this == otherPlay -> Result.DRAW
            else -> Result.LOSE
        }

    private fun isWin(otherPlay: Play) =
        when(otherPlay) {
            ROCK -> this == PAPER
            PAPER -> this == SCISSOR
            SCISSOR -> this == ROCK
        }

    companion object {
        fun fromString(string: String) = when (string) {
            "A", "X" -> ROCK
            "B", "Y" -> PAPER
            "C", "Z" -> SCISSOR
            else -> throw Error("Unable to parse string to Play")
        }
    }
}

enum class Result(
    val points: Int
) {
    LOSE(0),
    DRAW(3),
    WIN(6);

    fun getNeededPlay(opponentPlay: Play) =
        when {
            this == WIN -> getWinningPlay(opponentPlay)
            this == LOSE -> getLosingPlay(opponentPlay)
            else -> opponentPlay
        }

    private fun getWinningPlay(opponentPlay: Play) =
        when (opponentPlay) {
            Play.ROCK -> Play.PAPER
            Play.PAPER -> Play.SCISSOR
            Play.SCISSOR -> Play.ROCK
        }

    private fun getLosingPlay(opponentPlay: Play) =
        when (opponentPlay) {
            Play.ROCK -> Play.SCISSOR
            Play.PAPER -> Play.ROCK
            Play.SCISSOR -> Play.PAPER
        }
    companion object {
        fun fromString(string: String) = when (string) {
            "X" -> LOSE
            "Y" -> DRAW
            "Z" -> WIN
            else -> throw Error("Unable to parse string to Result")
        }
    }
}
