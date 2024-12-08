package day

import structures.Grid
import structures.Orientation
import structures.Orientation.NORTH
import structures.Orientation.SOUTH
import structures.Orientation.EAST
import structures.Orientation.WEST
import structures.Position



data object Day06 : Day {
    private const val OBSTACLE = '#'
    private const val GUARD_NORTH = '^'
    private const val GUARD_SOUTH = 'V'
    private const val GUARD_WEST = '<'
    private const val GUARD_EAST = '>'

    private fun Char.guardToOrientation() = when(this) {
        GUARD_NORTH -> NORTH
        GUARD_SOUTH -> SOUTH
        GUARD_WEST -> WEST
        GUARD_EAST -> EAST
        else -> throw RuntimeException()
    }

    data class SecurityRoom(
        override val gridValues: List<String>
    ) : Grid() {
        private val obstacles: Set<Position>
        var guard: Guard
            private set
        init {
            var guard: Guard? = null
            val obstacles = mutableSetOf<Position>()
            gridValues.forEachIndexed { y, line ->
                line.forEachIndexed { x, column ->
                    when (column) {
                        OBSTACLE -> obstacles.add(Position(y, x))
                        GUARD_NORTH, GUARD_SOUTH, GUARD_WEST, GUARD_EAST -> guard = Guard(position = Position(line = y, column = x), column.guardToOrientation())
                        else -> { }
                    }
                }
            }
            this.guard = guard!!
            this.obstacles = obstacles
        }

        fun isGuardInside(): Boolean = guard.position.line in 0..width && guard.position.column in 0..height
        fun guardPatrolling(): Guard {
            var nextMove = guard.move()
            nextMove = if (nextMove.position in obstacles) {
                guard.turnRight()
            } else {
                nextMove
            }
            guard = nextMove
            return nextMove
        }

        data class Guard(val position: Position, val orientation: Orientation) {
            fun move(): Guard =
                when (orientation) {
                    NORTH -> copy(position = position.copy(line = position.line - 1))
                    SOUTH -> copy(position = position.copy(line = position.line + 1))
                    EAST -> copy(position = position.copy(column = position.column + 1))
                    WEST -> copy(position = position.copy(column = position.column - 1))
                }

            fun turnRight(): Guard {
                val newOrientation = when(orientation) {
                    NORTH -> EAST
                    EAST -> SOUTH
                    SOUTH -> WEST
                    WEST -> NORTH
                }
                return copy(orientation = newOrientation)
            }
        }
    }

    override suspend fun part1(input: List<String>): Long {
        val cellsVisited = mutableSetOf<Position>()
        val securityRoom = SecurityRoom(input)

        var count = 0
        while (securityRoom.isGuardInside()) {
            val currentGuardPosition = securityRoom.guard.position
            securityRoom.guardPatrolling().let { if (currentGuardPosition != it.position) cellsVisited.add(it.position) }
            count++
            if (count % 1000 == 0) {
                println("cellsVisited ${cellsVisited.size} on iteration: $count")
            }
        }

        return cellsVisited.count().toLong()
    }

    override suspend fun part2(input: List<String>): Long = 0
}
