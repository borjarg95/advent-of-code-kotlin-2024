
object Day04 {

    fun part1(input: List<String> = inputXMAS.lines()): Int =
        input.flatMapIndexed { y, _ -> List(input.first().length) { x -> input.numXMAS(x, y) } }.sum()

    private fun List<String>.numXMAS(x: Int, y: Int): Int {
        val lineSize = this[y].length
        fun List<String>.isX(x: Int, y: Int) = this[y][x] == 'X'
        fun List<String>.isXMAS(x: Int, y: Int) = isX(x, y) && x + 3 < lineSize && this[y][x + 1] == 'M' && this[y][x + 2] == 'A' && this[y][x + 3] == 'S'
        fun List<String>.isRevertXMAS(x: Int, y: Int) = isX(x, y) && x - 3 >= 0 && this[y][x - 1] == 'M' && this[y][x - 2] == 'A' && this[y][x - 3] == 'S'
        fun List<String>.isDownXMAS(x: Int, y: Int) = isX(x, y) && y - 3 >= 0 && this[y - 1][x] == 'M' && this[y - 2][x] == 'A' && this[y - 3][x] == 'S'

        fun List<String>.isUpperXMAS(x: Int, y: Int) = isX(x, y) && y + 3 < lineSize && this[y + 1][x] == 'M' && this[y + 2][x] == 'A' && this[y + 3][x] == 'S'

        fun List<String>.isLeftUp(x: Int, y: Int) = isX(x, y) && x - 3 >= 0 && y - 3 >= 0 &&
            this[y - 1 ][x - 1] == 'M' && this[y - 2][x - 2] == 'A' && this[y - 3][x - 3] == 'S'

        fun List<String>.isLeftDown(x: Int, y: Int) = isX(x, y) && (x - 3) >= 0 && y + 3 < lineSize &&
            this[y + 1 ][x - 1] == 'M' && this[y + 2][x - 2] == 'A' && this[y + 3][x - 3] == 'S'

        fun List<String>.isRightUp(x: Int, y: Int) = isX(x, y) && x + 3 < lineSize && y + 3 < lineSize &&
            this[y + 1 ][x + 1] == 'M' && this[y + 2][x + 2] == 'A' && this[y + 3][x + 3] == 'S'

        fun List<String>.isRightDown(x: Int, y: Int) = isX(x, y) && x + 3 < lineSize && y - 3 >= 0 &&
            this[y - 1 ][x + 1] == 'M' && this[y - 2][x + 2] == 'A' && this[y - 3][x + 3] == 'S'

        return listOf(
            isXMAS(x, y), isRevertXMAS(x, y), isDownXMAS(x, y), isUpperXMAS(x, y),
            isLeftUp(x, y), isLeftDown(x, y), isRightUp(x, y), isRightDown(x, y),
        ).count { it }
    }
}

fun main() {
    val input = readInput("Day04")
    Day04.part1(input).println()
    Day04.part1(input).println()
}