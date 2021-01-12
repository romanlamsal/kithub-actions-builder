package de.lamsal.kithubactionsbuilder

class ValueMap(private val prefix: String, val indent: Int = 2) {
    private val values = mutableMapOf<String, String>()

    fun add(key: String, value: String) = values.put(key, value)

    fun isNotEmpty(): Boolean = values.isNotEmpty()

    infix fun String.to(that: String) {
        values[this] = that
    }

    override fun toString(): String {
        return if (values.isNotEmpty()) {
            "$prefix:\n" + values.entries.joinToString(separator = "\n") { (key, value) ->
                "  $key: $value"
            }
        } else
            ""
    }
}
