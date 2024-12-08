package day

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList


typealias Notes = Map<Int, MutableSet<Int>>

data object Day05 : Day {
    private fun List<String>.parseInput(): Pair<Notes, Sequence<List<Int>>> {
        val notes = mutableMapOf<Int, MutableSet<Int>>()
        val updates = mutableListOf(mutableListOf<Int>())
        forEach { line ->
            when {
                line.contains('|') ->
                    line.split('|')
                        .let { (x, y) ->
                            notes.compute(x.toInt()) { _, values ->
                                values?.add(y.toInt())?.let { values } ?: mutableSetOf(y.toInt())
                            }
                        }

                line.contains(',') ->
                    line.split(',')
                        .mapTo(mutableListOf(), String::toInt)
                        .let(updates::add)
            }
        }
        return notes to updates.asSequence().filterNot(Collection<Int>::isEmpty)
    }

    private fun List<Int>.middlePageNumber(): Long = this[this.size / 2].toLong()

    private fun List<Int>.isOrdered(notes: Notes): Boolean {
        val checked = mutableSetOf<Int>()
        return takeIf(List<Int>::isNotEmpty)?.all { value ->
            (notes.getOrDefault(value, emptySet()).none(checked::contains)
                    && checked.all { midValue -> notes[midValue]?.contains(value) == true }
                    ).also { checked.add(value) }
        } ?: false
    }

    override suspend fun part1(input: List<String>): Long {
        val (notes, updates) = input.parseInput()
        return updates.filter { it.isOrdered(notes) }.sumOf { it.middlePageNumber() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun part2(input: List<String>): Long {
        val (notes, updates) = input.parseInput()
        return updates
            .asFlow()
            .filterNot { it.isOrdered(notes) }
            .flatMapMerge { flow { emit(it.sortedWith(UpdateComparator(notes))) } }
            .toList()
            .sumOf { it.middlePageNumber() }
    }

    private class UpdateComparator(private val notes: Notes) : Comparator<Int> {
        override fun compare(o1: Int?, o2: Int?): Int = if (notes[o1]?.contains(o2) == true) -1 else 1
    }
}
