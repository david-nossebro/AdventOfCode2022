package day5.alternative

import utils.readFile
import utils.splitOnBlancLine
import utils.splitOnNewLine

fun main() {
    val input = readFile("day5.txt")

    println("Part 1: ${part1(input)}") //TDCHVHJTG
    println("Part 2: ${part2(input)}") //NGCMPJLHV
}

fun String.toStacks(): List<ArrayDeque<Char>> {

        val stacks = List(size = 9) { ArrayDeque<Char>() }

        splitOnBlancLine().first().splitOnNewLine().dropLast(1).map { row ->
            // Each Item is 4 letters on a row. Eg: "[G] "
            row.chunked(4)
                .mapIndexed {
                        index, item -> index to item[1] // [C] -> C
                }.filter { (_, item) -> item.isLetter() }
        }.forEach { row ->
            row.forEach { (index, item) ->
                stacks[index].addLast(item)
            }
        }

        return stacks
}

fun String.toInstructions() = splitOnBlancLine()[1].splitOnNewLine().map { instructionString ->
    val (nrOfItems, from, to) = instructionString.split(" ").mapNotNull{ it.toIntOrNull() }
    // Doing -1 to match index of list that store the stacks.
    Instruction(nrOfItems, from - 1, to - 1)
}

fun part1(input: String): String {

    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->
        repeat(instruction.nrOfItems) {
            stacks[instruction.from] moveOneTo stacks[instruction.to]
        }
    }

    return stacks.toTopItemsString()
}

fun part2(input: String): String {

    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->
        instruction.nrOfItems takeFromTopOf stacks[instruction.from] putOn stacks[instruction.to]
    }

    return stacks.toTopItemsString()
}

data class Instruction(val nrOfItems: Int, val from: Int, val to: Int)

infix fun ArrayDeque<Char>.moveOneTo(other: ArrayDeque<Char>) = other.addFirst(removeFirst())
infix fun List<Char>.putOn(stack: ArrayDeque<Char>) = reversed().forEach { stack.addFirst(it) }
infix fun Int.takeFromTopOf(stack: ArrayDeque<Char>): List<Char> = (0 until this).map { stack.removeFirst() }

fun  List<ArrayDeque<Char>>.toTopItemsString() =
    map { it.firstOrNull() ?: "" }.joinToString("")