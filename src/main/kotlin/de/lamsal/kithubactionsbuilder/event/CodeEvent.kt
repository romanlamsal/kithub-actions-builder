package de.lamsal.kithubactionsbuilder.event

open class CodeEvent(type: String) : WorkflowEvent(type) {
    var branches = emptyList<String>()
    var paths = emptyList<String>()

    override fun toString(): String = toYaml {
        append("$type:")
        append(branches.toBlock("branches"))
        append(paths.toBlock("paths"))
    }

    private fun List<String>.toBlock(blockType: String): String {
        if (isNotEmpty())
            return "\n  $blockType:\n" + joinToString(separator = "\n") { "  - $it" }.indentBlock()

        return ""
    }
}
