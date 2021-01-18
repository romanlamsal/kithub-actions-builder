package de.lamsal.kithubactionsbuilder

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

class DefaultsTest {

    @Test
    fun `should return empty string when no defaults are set`() {
        // given
        val defaults = Defaults()

        // when
        // do nothing

        // then
        assert(defaults.toString()).isEqualTo("")
    }

    @Test
    fun `should indent run defaults without other defaults`() {
        // given
        val defaults = Defaults()

        // when
        defaults.apply {
            run {
                "working-directory" to "foo"
            }
        }

        // then
        assert(defaults.toString()).isEqualTo("""
            defaults:
              run:
                working-directory: foo
        """.trimIndent())
    }

    @Test
    fun `should indent run defaults with general defaults`() {
        // given
        val defaults = Defaults()

        // when
        defaults.apply {
            "foo" to "bar"
            run {
                "working-directory" to "foo"
            }
        }

        // then
        assert(defaults.toString()).isEqualTo("""
            defaults:
              foo: bar
              run:
                working-directory: foo
        """.trimIndent())
    }

    @Test
    fun `should return general defaults without run`() {
        // given
        val defaults = Defaults()

        // when
        defaults.apply {
            "foo" to "bar"
        }

        // then
        assert(defaults.toString()).isEqualTo("""
            defaults:
              foo: bar
        """.trimIndent())
    }

    @Test
    fun `should not be empty when only run commands are set`() {
        // given
        val defaults = Defaults()

        // when
        defaults.apply {
            run {
                "working-directory" to "foo"
            }
        }

        // then
        assert(defaults.isEmpty()).isFalse()
        assert(defaults.isNotEmpty()).isTrue()
    }
}
