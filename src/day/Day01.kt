package day

import kotlin.math.abs

private fun List<String>.toIntLists(): Pair<List<Int>, List<Int>> =
    map { it.substringBefore(" ").toInt() to it.substringAfterLast(" ").toInt() }
        .unzip()

data object Day01 : Day {
    override fun part1(input: List<String>): Int {
        var (left, right) = input.toIntLists()
        left = left.sorted()
        right = right.sorted()

        return left.foldIndexed(0) { index, acc, value ->
            acc + abs(right[index] - value)
        }
    }

    override fun part2(input: List<String>): Int {
        val (left, right) = input.toIntLists()
        val countsRight = right.groupingBy { it }.eachCount()
        return left.sumOf { value -> value * (countsRight[value] ?: 0) }
    }
}


