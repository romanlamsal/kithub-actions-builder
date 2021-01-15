package de.lamsal.kithubactionsbuilder

open class Step(
    private val name: String? = null,
) : BlockElement() {
    var ifExpr: String by IfExpr()
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

    override fun toString(): String = with(StringBuilder()) {
        assert(uses != null || runCommands.isNotEmpty())

        if (name != null) appendLine("name: $name")
        append(ifExpr)
        if (uses != null) append(uses.toString())
        when (runCommands.size) {
            0 -> append("")
            1 -> appendLine("run: ${runCommands[0]}")
            else -> appendLine("run: |\n" + runCommands.joinToString(separator = "\n").indentBlock())
        }

        append(envVars)
    }.toString().indentBlock().replaceFirst(" ", "-").trimEnd()
}
