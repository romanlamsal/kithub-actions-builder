package de.lamsal.kithubactionsbuilder.event

import de.lamsal.kithubactionsbuilder.BlockElement

class On : BlockElement() {
    val events = mutableListOf<WorkflowEvent>()

    fun push(c: CodeEvent.() -> Unit = {}) = addCodeEvent("push", c)

    fun pullRequest(c: CodeEvent.() -> Unit = {}) = addCodeEvent("pull_request", c)

    private fun addCodeEvent(type: String, c: CodeEvent.() -> Unit = {}) = events.add(CodeEvent(type).apply(c))

    override fun toString(): String {
        return "on:\n" + events.joinToString(separator = "\n") { it.toString().indentBlock() }
    }
}
