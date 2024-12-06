package structures

data class Position(val line: Int, val column: Int) {
    override fun toString(): String = "[${line}, ${column}]"
}


