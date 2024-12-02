import kotlin.math.abs

private fun List<String>.toNumberedList(): Sequence<List<Int>> =
    this.map {
        it.split(" ").map { level -> level.toInt() }
    }.asSequence()

object Day02 {
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

    fun part1(input: List<String>): Int =
         input.toNumberedList()
            .count { it.validNumbers() }

    private fun produceInts(ints: List<Int>): Sequence<List<Int>> = sequence {
        yield(ints)
        ints.indices.forEach { index ->
            yield(ints.toMutableList().also { it.removeAt(index) })
        }
    }

    fun part2(input: List<String>): Int =
        input.toNumberedList()
            .count { produceInts(it).any { subList -> subList.validNumbers() }
        }
}

fun main() {
    val input = readInput("Day02")
    Day02.part1(input).println()
    Day02.part2(input).println()
}
