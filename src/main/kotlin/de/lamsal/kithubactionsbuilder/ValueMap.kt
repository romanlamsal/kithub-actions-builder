package de.lamsal.kithubactionsbuilder

open class ValueMap(
    private val prefix: String,
    val indent: Int = 2,
    private val givenValues: MutableMap<String, String> = mutableMapOf()
) : MutableMap<String, String> by givenValues, BlockElement() {

    fun add(key: String, value: String) = givenValues.put(key, value)

    open fun isNotEmpty(): Boolean = givenValues.isNotEmpty()

    infix fun String.to(that: String) {
        givenValues[this] = that
    }

    override fun toString(): String {
        return if (givenValues.isNotEmpty()) {
            "$prefix:\n" + givenValues.entries.joinToString(separator = "\n") { (key, value) ->
                "  $key: $value"
            }
        } else
            ""
    }
}
