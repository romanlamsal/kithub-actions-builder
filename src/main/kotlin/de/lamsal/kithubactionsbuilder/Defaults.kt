package de.lamsal.kithubactionsbuilder

class Defaults : ValueMap("defaults") {
    private val runDefaults = ValueMap("run")

    fun run(c: ValueMap.() -> Unit) = runDefaults.apply(c)

    override fun isEmpty(): Boolean = super.isEmpty() && runDefaults.isEmpty()
    override fun isNotEmpty(): Boolean = !isEmpty()

    override fun toString(): String = toYaml {
        if (runDefaults.isNotEmpty()) {
            appendLine(super.toString().ifEmpty { "defaults:" })
            append(runDefaults.toString().indentBlock())
        } else
            append(super.toString())
    }
}
