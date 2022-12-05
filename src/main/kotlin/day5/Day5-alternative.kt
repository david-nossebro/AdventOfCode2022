package day5.alternative

import utils.readFile
import utils.splitOnBlancLine
import utils.splitOnNewLine

fun main() {
    val input = readFile("day5.txt")

    println("Part 1: ${part1(input)}") //TDCHVHJTG
    println("Part 2: ${part2(input)}") //NGCMPJLHV
}

data class Instruction(val nrOfItems: Int, val from: Int, val to: Int)

fun String.toStacks() = splitOnBlancLine().first().splitOnNewLine().dropLast(1).map {
        it.chunked(4).map { it.trim() }
    }.fold(
        emptyMap<Int, ArrayDeque<Char>>().toMutableMap()
    ) {
        acc, item ->
        item.forEachIndexed { index, item ->
            if(item.isNotEmpty()) acc.getOrPut(index + 1) { ArrayDeque() }.addLast(item[1])
        }
        acc
    }

fun String.toInstructions() = splitOnBlancLine()[1].splitOnNewLine().map { instructionString ->
    val (nrOfItems, from, to) = instructionString.split("move ", " from ", " to ").drop(1).map{ it.toInt() }
    Instruction(nrOfItems, from, to)
}

infix fun ArrayDeque<Char>.moveOneTo(other: ArrayDeque<Char>) = other.addFirst(this.removeFirst())

fun  MutableMap<Int, ArrayDeque<Char>>.topItemsString() = toSortedMap().map { it.value.firstOrNull() ?: "" }.joinToString("")

fun part1(input: String): String {

    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->
        repeat(instruction.nrOfItems) {
            val fromStack = stacks[instruction.from] ?: error("Stack not found ${instruction.from}")
            val toStack = stacks[instruction.to] ?: error("Stack not found ${instruction.to}")
            fromStack moveOneTo toStack
        }
    }

    return stacks.topItemsString()
}


infix fun List<Char>.putOn(stack: ArrayDeque<Char>) = reversed().forEach { stack.addFirst(it) }
infix fun Int.takeFromTop(stack: ArrayDeque<Char>) = (0 until this).map { stack.removeFirst() }

fun part2(input: String): String {
    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->
        val fromStack = stacks[instruction.from] ?: error("Stack not found ${instruction.from}")
        val toStack = stacks[instruction.to] ?: error("Stack not found ${instruction.to}")

        val subStackOneTheMove = instruction.nrOfItems takeFromTop fromStack
        subStackOneTheMove putOn toStack
    }

    return stacks.topItemsString()
}
