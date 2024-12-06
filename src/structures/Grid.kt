package structures

abstract class Grid {
    val width: Int by lazy { this.gridValues.first().length }
    val height: Int by lazy { this.gridValues.size }

    abstract val gridValues: List<String>
}