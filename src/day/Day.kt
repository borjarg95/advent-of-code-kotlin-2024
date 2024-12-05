package day

import println
import readInput
import java.io.File

sealed interface Day {
    fun part1(input: List<String>): Int

    fun part2(input: List<String>): Int

    fun run() {
        val day = this.javaClass.simpleName
        println("Running $day")
        val input = readInput("input${File.separator}/$day")
        part1(input).println()
        part2(input).println()
    }
}
