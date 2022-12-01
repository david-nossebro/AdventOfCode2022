package day1

import utils.*

fun main() {
    val input = readFile("day1.txt")

    // Part 1
    input.splitOnBlancLine().maxOf { elfInventory ->
        elfInventory.splitOnNewLine().sum()
    }.let { println("Elf with most calories: $it") }

    // Part 2
    input.splitOnBlancLine().map { elfInventory ->
        elfInventory.splitOnNewLine().sum()
    }.sortedDescending().take(3).sum().let { println("Sum of 3 richest: $it") }
}