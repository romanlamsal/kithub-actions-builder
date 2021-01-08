open class Step(
    private val name: String? = null,
) : BlockElement() {
    var run: String? = null
    var uses: Uses? = null

    fun uses(name: String, c: Uses.() -> Unit = {}) {
        uses = Uses(name).apply(c)
    }

    override fun toString(): String {
        val nameLine = if (name != null) "name: $name" else ""
        val runLines: String = run.let {
            return@let if (it == null)
                ""
            else {
                val numLines: Int = it.lines().size
                "run: " + if (numLines > 1) {
                    "|\n" + it.indentBlock()
                } else {
                    it
                }
            }
        }

        val usesLines = uses?.toString() ?: ""

        val stepBlock = listOf(nameLine, runLines, usesLines)
            .filter { it.isNotEmpty() }
            .joinToString(separator = "\n")

        return stepBlock.indentBlock().replaceFirst(" ", "-")
    }
}
