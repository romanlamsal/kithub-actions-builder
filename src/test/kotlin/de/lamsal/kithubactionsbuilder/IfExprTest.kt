package de.lamsal.kithubactionsbuilder

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class IfExprTest {

    @Test
    fun `should return empty string when nothing was set before`() {
        // given
        val wrapper = Wrapper()

        // when
        // wrapper.ifExpr is not set

        // then
        assert(wrapper.toString()).isEqualTo("")
    }

    @Test
    fun `should return prefixed string when expression is set`() {
        // given
        val ifExpr = "foo"
        val wrapper = Wrapper()

        // when
        wrapper.ifExpr = ifExpr

        // then
        assert(wrapper.toString()).isEqualTo("if: $ifExpr\n")
    }

    @Test
    fun `should return indented and prefixed string when expression is set and indent is given`() {
        // given
        val ifExpr = "foo"
        val wrapper = Wrapper(indent = 2)

        // when
        wrapper.ifExpr = ifExpr

        // then
        assert(wrapper.toString()).isEqualTo("  if: $ifExpr\n")
    }

    private inner class Wrapper(indent: Int = 0) : BlockElement() {
        var ifExpr: String by IfExpr(indent)

        override fun toString(): String {
            return ifExpr
        }
    }
}
