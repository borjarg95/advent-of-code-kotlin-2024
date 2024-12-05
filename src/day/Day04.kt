package day

data object Day04 : Day {
    private const val X = 'X'
    private const val M = 'M'
    private const val A = 'A'
    private const val S = 'S'
    override fun part1(input: List<String>): Int = input.flatMapIndexed { y, _ -> List(input.first().length) { x -> input.numXMAS(x, y) } }.sum()

    override fun part2(input: List<String>): Int = input.flatMapIndexed { y, _ -> List(input.first().length) { x -> input.isCrossMas(x, y) } }.count { it }

    private fun List<String>.isCrossMas(x: Int, y: Int): Boolean {
        val upLeft by lazy { this[y - 1][x - 1] }
        val downRight by lazy { this[y + 1][x + 1] }
        val upRight by lazy { this[y - 1][x + 1] }
        val downLeft by lazy { this[y + 1][x - 1] }

        fun isMasLeftDiagonal(): Boolean = downLeft == M && upRight == S || downLeft == S && upRight == M
        fun isMasRightDiagonal(): Boolean = downRight == M && upLeft == S || downRight == S && upLeft == M
        fun List<String>.isValidStart(): Boolean = x > 0 && x < first().length - 1 && y > 0 && y < size - 1 && this[y][x] == A

        return isValidStart() && isMasRightDiagonal() && isMasLeftDiagonal()
    }


    private fun List<String>.numXMAS(x: Int, y: Int): Int {
        val lineSize = this[y].length

        fun List<String>.isX(x: Int, y: Int) = this[y][x] == X
        fun List<String>.isXMAS(x: Int, y: Int) = isX(x, y) && x + 3 < lineSize && this[y][x + 1] == M && this[y][x + 2] == A && this[y][x + 3] == S
        fun List<String>.isRevertXMAS(x: Int, y: Int) = isX(x, y) && x - 3 >= 0 && this[y][x - 1] == M && this[y][x - 2] == A && this[y][x - 3] == S
        fun List<String>.isDownXMAS(x: Int, y: Int) = isX(x, y) && y - 3 >= 0 && this[y - 1][x] == M && this[y - 2][x] == A && this[y - 3][x] == S

        fun List<String>.isUpperXMAS(x: Int, y: Int) = isX(x, y) && y + 3 < lineSize && this[y + 1][x] == M && this[y + 2][x] == A && this[y + 3][x] == S

        fun List<String>.isLeftUp(x: Int, y: Int) = isX(x, y) && x - 3 >= 0 && y - 3 >= 0 &&
            this[y - 1 ][x - 1] == M && this[y - 2][x - 2] == A && this[y - 3][x - 3] == S

        fun List<String>.isLeftDown(x: Int, y: Int) = isX(x, y) && (x - 3) >= 0 && y + 3 < lineSize &&
            this[y + 1 ][x - 1] == M && this[y + 2][x - 2] == A && this[y + 3][x - 3] == S

        fun List<String>.isRightUp(x: Int, y: Int) = isX(x, y) && x + 3 < lineSize && y + 3 < lineSize &&
            this[y + 1 ][x + 1] == M && this[y + 2][x + 2] == A && this[y + 3][x + 3] == S

        fun List<String>.isRightDown(x: Int, y: Int) = isX(x, y) && x + 3 < lineSize && y - 3 >= 0 &&
            this[y - 1 ][x + 1] == M && this[y - 2][x + 2] == A && this[y - 3][x + 3] == S

        return listOf(
            isXMAS(x, y),
            isRevertXMAS(x, y),
            isDownXMAS(x, y),
            isUpperXMAS(x, y),
            isLeftUp(x, y),
            isLeftDown(x, y),
            isRightUp(x, y),
            isRightDown(x, y),
        ).count { it }
    }
}
