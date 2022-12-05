package day5

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
        acc, strings ->
        strings.forEachIndexed {
                index, item ->
            if(item.isNotEmpty()) {
                when {
                    acc.containsKey(index + 1) -> acc[index + 1]!!.addLast(item[1])
                    else -> acc[index + 1] = ArrayDeque(listOf(item[1]))
                }
            }
        }
        acc
    }

fun String.toInstructions() = splitOnBlancLine()[1].splitOnNewLine().map {
    val (nrOfItems, from, to) = it.split("move ", " from ", " to ").drop(1).map{ it.toInt() }
    Instruction(nrOfItems, from, to)
}



fun part1(input: String): String {

    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->
        repeat(instruction.nrOfItems) {
            stacks[instruction.to]?.addFirst(
                stacks[instruction.from]?.removeFirst() ?: error("Stack not found ${instruction.from}")
            ) ?: error("Stack not found ${instruction.to}")
        }
    }

    return stacks.toSortedMap().map { it.value.firstOrNull() ?: "" }.joinToString("")
}

fun ArrayDeque<Char>.addAllFirst(items: List<Char>) = items.reversed().forEach { addFirst(it) }
fun ArrayDeque<Char>.removeAllFirst(nrOfItems: Int) = (0..nrOfItems).map { this.removeFirst() }
fun part2(input: String): String {
    val stacks = input.toStacks()
    val instructions = input.toInstructions()

    instructions.forEach { instruction ->
        val itemsToBeMoved = stacks[instruction.from]?.removeAllFirst(instruction.nrOfItems - 1) ?:
            error("Stack not found: ${instruction.from}")
        stacks[instruction.to]?.addAllFirst(itemsToBeMoved) ?: error("Stack not found ${instruction.to}")
    }

    return stacks.toSortedMap().map { it.value.firstOrNull() ?: "" }.joinToString("")
}
