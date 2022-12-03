package day3

import utils.readFile
import utils.splitOnNewLine

fun main() {
    val input = readFile("day3.txt")

    // Part 1
    val itemTypePrioMap = (('a'..'z') + ('A'..'Z')).zip((1..52)).toMap()

    input.splitOnNewLine().map { row ->
        row.chunked(row.length / 2).map { it.toSet() }
            .let { (firstCompartment, secondCompartment) ->
                firstCompartment.intersect(secondCompartment).first()
        }
    }.sumOf {
        itemType -> itemTypePrioMap[itemType] ?: error("Prio not found for item type $itemType")
    }.let { println("P1: Total prio: $it") }

    // Part 2
    input.splitOnNewLine().map { it.toSet() }.chunked(3).map { group ->
        group.reduce{ acc, items -> acc.intersect(items) }.first()
    }.sumOf {
        badgeItemType ->  itemTypePrioMap[badgeItemType] ?: error("Prio not found for item type $badgeItemType")
    }.let { println("P2: Total prio for badges: $it") }
}