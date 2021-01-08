data class Uses(
    var name: String,
) : BlockElement() {
    var with: MutableMap<String, String> = emptyMap<String, String>().toMutableMap()

    infix fun String.being(that: String) {
        with[this] = that
    }

    override fun toString(): String {
        return "uses: $name" + if (with.isNotEmpty())
            "\nwith:\n" + with.entries.joinToString(separator = "\n") {
                "${it.key}: ${it.value}"
            }.indentBlock()
        else
            ""
    }
}
