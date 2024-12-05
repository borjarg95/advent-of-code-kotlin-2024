package day

typealias Updates = List<List<Int>>
typealias Notes = Map<Int, MutableSet<Int>>

data object Day05 : Day {
    private fun List<String>.parseInput(): Pair<Notes, Updates> {
        val notes  = mutableMapOf<Int, MutableSet<Int>>()
        val updates = mutableListOf(mutableListOf<Int>())
        forEach { line ->
            when {
                line.contains('|') ->
                    line.split('|')
                        .let { (x, y) -> notes[x.toInt()]?.add(y.toInt()) ?: mutableSetOf(y.toInt()) }
                line.contains(',') ->
                    line.split(',')
                        .mapTo(mutableListOf(), String::toInt)
                        .let(updates::add)
            }

        }
        return notes to updates
    }


    override fun part1(input: List<String>): Int {
        TODO("Not yet implemented")
    }

    override fun part2(input: List<String>): Int {
        TODO("Not yet implemented")
    }
}