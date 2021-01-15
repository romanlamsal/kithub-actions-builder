package de.lamsal.kithubactionsbuilder

import de.lamsal.kithubactionsbuilder.event.On

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

    override fun toString(): String = toYaml {
        val onBlock = onEvent.toString()

        val jobBlocks = jobs.joinToString(separator = "\n") { it.toString().indentBlock() }

        appendLine("name: $name\n")
        appendLine(onBlock)
        appendLineNonEmpty(envVars) { "\n$envVars" }
        appendLine("\njobs:\n$jobBlocks")
    }

    fun env(c: ValueMap.() -> Unit) = envVars.apply(c)
}

fun workflow(name: String, c: Workflow.() -> Unit): String = Workflow(name).apply(c).toString()
