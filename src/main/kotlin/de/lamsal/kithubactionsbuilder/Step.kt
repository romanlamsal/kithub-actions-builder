package de.lamsal.kithubactionsbuilder

open class Step(
    private val name: String? = null,
) : BlockElement() {
    var ifExpr: String? = null
    val runCommands = mutableListOf<String>()
    private val envVars = ValueMap("env")
    var uses: Uses? = null

    fun uses(name: String, c: ValueMap.() -> Unit = {}) {
        uses = Uses(name).apply { with.apply(c) }
    }

    fun run(vararg commands: String) {
        runCommands.addAll(commands.flatMap { it.lines() })
    }

    fun env(c: ValueMap.() -> Unit) = envVars.apply(c)

    override fun toString(): String {
        assert(uses != null || runCommands.isNotEmpty())

        val builder = StringBuilder()

        if (name != null) builder.append("name: $name\n")
        if (ifExpr != null) builder.append("if: $ifExpr\n")
        if (uses != null) builder.append(uses.toString())
        when (runCommands.size) {
            0 -> builder.append("")
            1 -> builder.append("run: ${runCommands[0]}\n")
            else -> builder.append("run: |\n" + runCommands.joinToString(separator = "\n").indentBlock() + "\n")
        }

        builder.append(envVars)

        return builder.toString().indentBlock().replaceFirst(" ", "-").trimEnd()
    }
}
