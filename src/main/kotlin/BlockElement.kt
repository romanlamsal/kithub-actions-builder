@DslMarker
annotation class ActionElement

@ActionElement
open class BlockElement {
    fun String.indentBlock(indent: Int = 2): String = toString().lines()
        .joinToString(separator = "\n") {
            it.prependIndent(" ".repeat(indent))
        }
}
