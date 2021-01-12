package de.lamsal.kithubactionsbuilder

class Workflow(
    private val name: String,
) : BlockElement() {
    private val jobs = emptyList<Job>().toMutableList()
    private lateinit var onEvent: On
    private val envVars = ValueMap("env")

    fun job(name: String, c: Job.() -> Unit) = jobs.add(Job(name).apply(c))

    fun on(c: On.() -> Unit) {
        this.onEvent = On().apply(c)
    }

    override fun toString(): String {
        val onBlock = onEvent.toString()

        val jobBlocks = jobs.joinToString(separator = "\n") { it.toString().indentBlock() }

        return """
name: $name

$onBlock
${if (envVars.isNotEmpty()) "\n$envVars\n" else ""}
jobs:
$jobBlocks
        """.trimIndent()
    }

    fun env(c: ValueMap.() -> Unit) = envVars.apply(c)
}

fun workflow(name: String, c: Workflow.() -> Unit): String = Workflow(name).apply(c).toString()
