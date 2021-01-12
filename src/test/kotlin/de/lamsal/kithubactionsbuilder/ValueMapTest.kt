package de.lamsal.kithubactionsbuilder

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class ValueMapTest {

    @Test
    fun `should print an empty string when no values were given`() {
        // given
        val valueMap = ValueMap("")

        // when
        val valueMapString = valueMap.toString()

        // then
        assert(valueMapString).isEqualTo("")
    }

    @Test
    fun `should print prefix followed by indented values`() {
        // given
        val prefix = "env"
        val key = "foo"
        val value = "bar"
        val valueMap = ValueMap(prefix)

        // when
        valueMap.add(key, value)
        valueMap.add("${key}2", value)
        val valueMapString = valueMap.toString()

        // then
        assert(valueMapString).isEqualTo("""
            $prefix:
              $key: $value
              ${key}2: $value
        """.trimIndent())
    }
}