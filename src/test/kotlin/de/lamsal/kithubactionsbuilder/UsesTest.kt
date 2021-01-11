package de.lamsal.kithubactionsbuilder

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class UsesTest {

    @Test
    fun `should return 'uses' directive from given name`() {
        // given
        val name = "foobar/baz@v1"

        // when
        val result = Uses(name).toString()

        // then
        assert(result).isEqualTo("""
            uses: $name
        """.trimIndent())
    }

    @Test
    fun `should return 'uses' directive from given name and with 'with' block`() {
        // given
        val name = "foobar/baz@v1"

        // when
        val uses = Uses(name)
        uses.with["foo"] = "bar"
        val result = uses.toString()

        // then
        assert(result).isEqualTo("""
            uses: $name
            with:
              foo: bar
        """.trimIndent())
    }
}
