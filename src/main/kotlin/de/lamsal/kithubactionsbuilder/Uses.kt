package de.lamsal.kithubactionsbuilder

data class Uses(
    private val name: String,
) : BlockElement() {
    val with = ValueMap("with")

    override fun toString(): String = toYaml {
        appendLine("uses: $name")
        appendNonEmpty(with)
    }
}
