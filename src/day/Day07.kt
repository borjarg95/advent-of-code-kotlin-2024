package day

import java.awt.SecondaryLoop
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

data object Day07: Day {
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun List<String>.parseInput(): Flow<Pair<Long, List<Long>>> = this.asFlow()
        .flatMapMerge { line ->
            flow {
                val (result, numbers) = line.split(':')
                emit(result.toLong() to numbers.trim().split(' ').map(String::toLong))
            }
        }
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun part1(input: List<String>): Long {
        return input.parseInput().flatMapMerge { (result, values) ->
            flow {
                val value = if (isCorrect(values, 0, expected = result)) result else 0
                emit(value)
            }
        }.toList()
        .sum()
    }

    override suspend fun part2(input: List<String>): Long {
        TODO("Not yet implemented")
    }

    private fun isCorrect(
        values: List<Long>,
        index: Int,
        acc: Long = 0,
        expected: Long
    ): Boolean {
        if (index == values.size) return acc == expected

        return isCorrect(values, index+1, acc + values[index], expected)
            || isCorrect(values, index+1, acc * values[index], expected)
    }
}