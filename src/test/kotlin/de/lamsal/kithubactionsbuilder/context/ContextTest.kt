package de.lamsal.kithubactionsbuilder.context

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class ContextTest {

    @Test
    fun `should embed given value in github interpolation context`() {
        // given
        val value = "github.token"

        // when
        val interpolated = context(value)

        // then
        assert(interpolated).isEqualTo("\${{ $value }}")
    }

    @Test
    fun `should prepend 'github' on Context#github`() {
        // given
        val value = "token"

        // when
        val interpolated = context.github(value)

        // then
        assert(interpolated).isEqualTo("\${{ github.$value }}")
    }

    @Test
    fun `should prepend 'secret' on Context#secrets`() {
        // given
        val value = "something"

        // when
        val interpolated = context.secrets(value)

        // then
        assert(interpolated).isEqualTo("\${{ secrets.$value }}")
    }
}
