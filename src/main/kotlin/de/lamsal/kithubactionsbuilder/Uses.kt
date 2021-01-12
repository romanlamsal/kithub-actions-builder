package de.lamsal.kithubactionsbuilder

data class Uses(
    var name: String,
) : BlockElement() {
    val with = ValueMap("with")

    override fun toString(): String {
        return "uses: $name\n" + if (with.isNotEmpty()) "$with" else ""
    }
}
