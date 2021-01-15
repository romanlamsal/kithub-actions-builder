package de.lamsal.kithubactionsbuilder

import kotlin.reflect.KProperty

class IfExpr(private val indent: Int = 0) {
    private var expression: String = ""

    operator fun getValue(element: BlockElement, property: KProperty<*>): String = expression

    operator fun setValue(element: BlockElement, property: KProperty<*>, expr: String) {
        expression = "${" ".repeat(indent)}if: $expr\n"
    }
}
