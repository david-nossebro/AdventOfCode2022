package day6

import utils.readFile

fun main() {
    require(part1(readFile("day6-test.txt")) == 7)
    println("P1: ${part1(readFile("day6.txt"))}")

    require(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 19)
    require(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    require(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    require(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    require(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)
    println("P2:  ${part2(readFile("day6.txt"))}")
}

fun part1(input: String) = input.findMarkerIndex(4)
fun part2(input: String) = input.findMarkerIndex(14)

private fun String.findMarkerIndex(wSize: Int) =
    windowed(wSize).indexOfFirst { it.toSet().size == wSize } + wSize