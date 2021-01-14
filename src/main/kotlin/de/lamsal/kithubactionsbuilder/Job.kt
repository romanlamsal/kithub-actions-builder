package de.lamsal.kithubactionsbuilder

import java.lang.StringBuilder

data class Job(
    private val name: String,
    private val runsOn: String = "ubuntu-latest",
) : BlockElement() {
    var ifExpr: String? = null
    private val steps = mutableListOf<Step>()

    fun step(name: String? = null, c: Step.() -> Unit) = steps.add(Step(name).apply(c))

    fun checkout() = steps.add(Step().apply {
        uses = Uses("actions/checkout@v1")
    })

    override fun toString(): String {
        assert(steps.isNotEmpty())

        return StringBuilder().apply {
            appendLine("$name:")
            appendLine("  runs-on: $runsOn")
            if (ifExpr != null) appendLine("  if: $ifExpr")
            appendLine("  steps:")
        }.toString() + steps.joinToString(separator = "\n") { it.toString().indentBlock(4) }
    }
}
