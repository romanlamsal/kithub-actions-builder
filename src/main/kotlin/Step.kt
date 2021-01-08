open class Step(
    private val name: String? = null,
) : BlockElement() {
    var runCommands: MutableList<String> = emptyList<String>().toMutableList()
    var uses: Uses? = null

    fun uses(name: String, c: Uses.() -> Unit = {}) {
        uses = Uses(name).apply(c)
    }

    fun run(vararg commands: String) {
        runCommands.addAll(commands.flatMap { it.lines() })
    }

    override fun toString(): String {
        assert(uses != null || runCommands.isNotEmpty())

        val builder = StringBuilder()

        if (name != null) builder.append("name: $name\n")
        if (uses != null) builder.append(uses.toString())
        when (runCommands.size) {
            0 -> builder.append("")
            1 -> builder.append("run: ${runCommands[0]}")
            else -> builder.append("run: |\n" + runCommands.joinToString(separator = "\n").indentBlock())
        }

        return builder.toString().indentBlock().replaceFirst(" ", "-")
    }
}
