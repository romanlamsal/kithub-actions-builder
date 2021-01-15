package de.lamsal.kithubactionsbuilder

data class Job(
    private val name: String,
    private val runsOn: String = "ubuntu-latest",
) : BlockElement() {
    var ifExpr: String by IfExpr(indent = 2)
    private val steps = mutableListOf<Step>()

    fun step(name: String? = null, c: Step.() -> Unit) = steps.add(Step(name).apply(c))

    fun checkout() = steps.add(Step().apply {
        uses = Uses("actions/checkout@v1")
    })

    override fun toString(): String = toYaml {
        assert(steps.isNotEmpty())

        appendLine("$name:")
        appendLine("  runs-on: $runsOn")
        append(ifExpr)
        appendLine("  steps:")
        append(steps.indent())
    }

    private fun MutableList<Step>.indent(): String {
        return joinToString(separator = "\n") { it.toString().indentBlock(4) }
    }
}
