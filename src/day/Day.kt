package day

import readInput
import java.io.File

sealed interface Day {
    suspend fun part1(input: List<String>): Long

    suspend fun part2(input: List<String>): Long

    suspend fun run() {
        val day = this.javaClass.simpleName
        println("Running $day")
        val input = readInput("input${File.separator}/$day")
        println("part1: ${part1(input)}")
        println("part2: ${part2(input)}")
    }
}
