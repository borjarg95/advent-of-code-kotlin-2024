package day

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

typealias Operation = (Long, Long) -> Long

data object Day07: Day {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun part1(input: List<String>): Long {
        return input.parseInput().flatMapMerge { (result, values) ->
            flow {
                val value = if (result.isCorrect(values, listOf(sum, mul))) result else 0
                emit(value)
            }
        }.toList()
        .sum()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun part2(input: List<String>): Long {
        return input.parseInput()
            .flatMapMerge { (result, values) ->
                flow {
                    val value = if (result.isCorrect(values, listOf(sum, mul, concat))) result else 0
                    emit(value)
                }
            }.toList()
            .sum()
    }

    private fun Long.isCorrect(
        values: List<Long>,
        operations: List<Operation>,
        index: Int = 0,
        acc: Long = 0,
    ): Boolean {
        return when {
            acc > this -> false
            index == values.size -> acc == this
            else -> operations.any {
                isCorrect(values, operations = operations, index = index + 1, acc = it(acc, values[index]))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun List<String>.parseInput(): Flow<Pair<Long, List<Long>>> = this.asFlow()
        .flatMapMerge { line ->
            flow {
                val (result, numbers) = line.split(':')
                emit(result.toLong() to numbers.trim().split(' ').map(String::toLong))
            }
        }

    private val sum: Operation = { a, b -> a + b}
    private val mul: Operation = { a, b -> a * b}
    private val concat: (Long, Long) -> Long = { a, b -> "$a$b".toLong() }
}