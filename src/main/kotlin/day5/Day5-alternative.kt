package day5.alternative

import utils.readFile
import utils.splitOnBlancLine
import utils.splitOnNewLine

fun main() {
    val input = readFile("day5.txt")

    println("Part 1: ${part1(input)}") //TDCHVHJTG
    println("Part 2: ${part2(input)}") //NGCMPJLHV
}

fun String.toStacks() = splitOnBlancLine().first().splitOnNewLine().dropLast(1).map { row ->
        // Each Item is 4 letters on a row. Eg: "[G] "
        row.chunked(4).map { it.trim() }
            .mapIndexed{ index, item -> index to item }.filter { it.second.isNotEmpty() }
            .map { it.first to it.second[1] } // [C] -> C
    }.fold(
        emptyMap<Int, ArrayDeque<Char>>().toMutableMap()
    ) {
        acc, itemsOnRow ->
        itemsOnRow.forEach { (index, item) ->
            acc.getOrPut(index + 1) { ArrayDeque() }.addLast(item)
        }
        acc
    }

fun String.toInstructions() = splitOnBlancLine()[1].splitOnNewLine().map { instructionString ->
    val (nrOfItems, from, to) = instructionString.split("move ", " from ", " to ").drop(1).map{ it.toInt() }
    Instruction(nrOfItems, from, to)
}

fun part1(input: String): String {

    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->

        val fromStack = stacks[instruction.from] ?: error("Stack not found ${instruction.from}")
        val toStack = stacks[instruction.to] ?: error("Stack not found ${instruction.to}")

        repeat(instruction.nrOfItems) {
            fromStack moveOneTo toStack
        }
    }

    return stacks.toTopItemsString()
}

fun part2(input: String): String {

    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->
        val fromStack = stacks[instruction.from] ?: error("Stack not found ${instruction.from}")
        val toStack = stacks[instruction.to] ?: error("Stack not found ${instruction.to}")
        instruction.nrOfItems takeFromTopOf fromStack putOn toStack
    }

    return stacks.toTopItemsString()
}

data class Instruction(val nrOfItems: Int, val from: Int, val to: Int)

infix fun ArrayDeque<Char>.moveOneTo(other: ArrayDeque<Char>) = other.addFirst(removeFirst())
infix fun List<Char>.putOn(stack: ArrayDeque<Char>) = reversed().forEach { stack.addFirst(it) }
infix fun Int.takeFromTopOf(stack: ArrayDeque<Char>) = (0 until this).map { stack.removeFirst() }
fun  MutableMap<Int, ArrayDeque<Char>>.toTopItemsString() =
    toSortedMap().map { it.value.firstOrNull() ?: "" }.joinToString("")