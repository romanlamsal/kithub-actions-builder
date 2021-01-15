package de.lamsal.kithubactionsbuilder

@DslMarker
annotation class ActionElement

@ActionElement
open class BlockElement {

    fun String.indentBlock(indent: Int = 2): String = toString().lines()
        .joinToString(separator = "\n") {
            it.prependIndent(" ".repeat(indent))
        }

    fun toYaml(c: StringBuilder.() -> Unit) = StringBuilder().apply(c).toString().trim()

    protected fun StringBuilder.appendLineNonEmpty(
        value: Map<*, *>,
        nonNullValue: () -> String = { value.toString() }
    ) = appendNonEmpty(value) { nonNullValue() + "\n" }

    protected fun StringBuilder.appendNonEmpty(
        value: Map<*, *>,
        nonNullValue: () -> String = { value.toString() }
    ) {
        if (value.isNotEmpty())
            append(nonNullValue())
    }

    protected fun StringBuilder.appendLineNonNull(value: Any?, nonNullValue: () -> String = { value.toString() }) =
        appendNonNull(value) { nonNullValue() + "\n" }

    protected fun StringBuilder.appendNonNull(value: Any?, nonNullValue: () -> String = { value.toString() }) {
        if (value != null)
            append(nonNullValue())
    }
}
