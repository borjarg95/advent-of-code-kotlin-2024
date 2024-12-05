package day

import kotlin.math.abs

private fun List<String>.toNumberedList(): Sequence<List<Int>> =
    map {
        it.split(" ").map { level -> level.toInt() }
    }.asSequence()

data object Day02: Day {
    private fun List<Int>.validNumbers(): Boolean {
        var order: Int? = null
        return this.zipWithNext()
            .all { (first, second) ->
                if (abs(first - second) in 1..3) {
                    when(order) {
                        -1 -> first > second
                        1 -> second > first
                        else -> {
                            order = calculateOrder(first, second)
                            true
                        }
                    }
                }
                else false
            }
    }

    private fun calculateOrder(first: Int, second: Int) = if (first > second) -1 else 1

    override fun part1(input: List<String>): Int =
         input.toNumberedList()
            .count { it.validNumbers() }

    private fun produceInts(ints: List<Int>): Sequence<List<Int>> = sequence {
        yield(ints)
        ints.indices.forEach { index ->
            yield(ints.toMutableList().also { it.removeAt(index) })
        }
    }

    override fun part2(input: List<String>): Int =
        input.toNumberedList()
            .count { produceInts(it).any { subList -> subList.validNumbers() }
        }
}
