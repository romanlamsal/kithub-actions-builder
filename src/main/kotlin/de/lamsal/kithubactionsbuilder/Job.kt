package de.lamsal.kithubactionsbuilder

data class Job(
    var name: String? = null,
    var runsOn: String = "ubuntu-latest",
) : BlockElement() {
    private val steps = emptyList<Step>().toMutableList()

    fun step(name: String? = null, c: Step.() -> Unit) = steps.add(Step(name).apply(c))

    fun checkout() = steps.add(Step().apply {
        uses = Uses("actions/checkout@v1")
    })

    override fun toString(): String {
        assert(steps.isNotEmpty())

        return """
            $name:
              runs-on: $runsOn
              steps:
        """.trimIndent() + steps.joinToString(prefix = "\n", separator = "\n") { it.toString().indentBlock(4) }
    }
}
