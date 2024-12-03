object Day03 {
    private const val REGEX_PART_1 = """mul\(\d{1,3},\d{1,3}\)"""
    private const val REGEX_PART_2 = """mul\((\d{1,3}),(\d{1,3})\)|don't\(\)|do\(\)"""

    fun part1(input: List<String>): Int =
         input.joinToString("")
             .let { REGEX_PART_1.toRegex().findAll(it) }
             .flatMap { match -> match.groupValues }
             .sumOf(String::mulMultiply)

    fun part2(input: List<String>): Int {
        var active = true
        return input.joinToString("")
            .let { REGEX_PART_2.toRegex().findAll(it) }
            .map { it.groupValues[0] }
            .foldIndexed(0) { index, acc, current ->
                when (current) {
                    "do()" -> 0.also { active = true }
                    "don't()" -> 0.also { active = false }
                    else -> if (active) current.mulMultiply() else 0
                } + acc
            }
    }
}

private fun String.mulMultiply(): Int {
    val (first, second) = removePrefix("mul(")
        .removeSuffix(")")
        .split(",")
    return first.toInt() * second.toInt()
}

fun main() {
    val input = readInput("Day03")
    Day03.part1(input).println()
    Day03.part2(input).println()
}
