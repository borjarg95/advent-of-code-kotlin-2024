object Day03 {
    private const val REGEX_EXP = """mul\(\d{1,3},\d{1,3}\)"""
    private const val PART_2_BEFORE_DONT = """(mul\(\d{1,3},\d{1,3}\))(?=(?!.*don't\().*don't\()"""
    private const val PART_2_AFTER_DO = """do\(\)[^m]*?(mul\(\d{1,3},\d{1,3}\))"""

    fun part1(input: List<String>): Int =
         input.joinToString("")
             .let { REGEX_EXP.toRegex().findAll(it) }
             .flatMap { match -> match.groupValues }
             .sumOf { match ->
                 val (first, second) = match.removePrefix("mul(")
                     .removeSuffix(")")
                     .split(",")
                 first.toInt() * second.toInt()
             }

    fun part2(input: List<String>): Int =
        input.joinToString("")
            .let {
                val matchesBeforeDont = PART_2_BEFORE_DONT.toRegex().findAll(it).map { matchResult -> matchResult.groupValues[1] }.toList()
                val matchesAfterDo = PART_2_AFTER_DO.toRegex().findAll(it).map { matchResult -> matchResult.groupValues[1] }.toList()
                matchesBeforeDont.plus(matchesAfterDo)
            }.sumOf { match ->
                val (first, second) = match.removePrefix("mul(")
                    .removeSuffix(")")
                    .split(",")
                first.toInt() * second.toInt()
            }

}

fun main() {
    val input = readInput("Day03")
    Day03.part1(input).println()
    Day03.part2(input).println()
}
