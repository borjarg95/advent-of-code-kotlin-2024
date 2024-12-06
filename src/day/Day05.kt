package day


typealias Notes = Map<Int, MutableSet<Int>>

data object Day05 : Day {
    private fun List<String>.parseInput(): Pair<Notes, List<List<Int>>> {
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
        return notes to updates
    }

    private fun List<Int>.middlePageNumber(): Int = this[this.size / 2]

    private fun List<Int>.isOrdered(notes: Notes): Boolean {
        val checked = mutableSetOf<Int>()
        return takeIf(List<Int>::isNotEmpty)?.all { value ->
            (notes.getOrDefault(value, emptySet()).none(checked::contains)
                && checked.all { midValue -> notes[midValue]?.contains(value) == true }
            ).also { checked.add(value) }
        } ?: false
    }

    override fun part1(input: List<String>): Int {
        val (notes, updates) = input.parseInput()
        return updates.filter { it.isOrdered(notes) }.sumOf { it.middlePageNumber() }
    }

    override fun part2(input: List<String>): Int = 0
}
