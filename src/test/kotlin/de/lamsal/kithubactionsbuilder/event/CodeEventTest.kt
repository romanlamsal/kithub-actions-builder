package de.lamsal.kithubactionsbuilder.event

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class CodeEventTest {

    @Test
    fun `should append branches as yaml list on toString`() {
        // given
        val type = "foobarbaz"
        val eventBranches = listOf("foo", "bar", "baz")
        val event = CodeEvent(type).apply {
            branches = eventBranches
        }

        // when
        val eventBlock = event.toString()

        // then
        assert(eventBlock).isEqualTo(
            """
            $type:
              branches:
                - ${eventBranches[0]}
                - ${eventBranches[1]}
                - ${eventBranches[2]}
        """.trimIndent()
        )
    }

    @Test
    fun `should append paths as yaml list on toString`() {
        // given
        val type = "foobarbaz"
        val eventPaths = listOf("src/**/*", "!src/test/**/*")
        val event = CodeEvent(type).apply {
            paths = eventPaths
        }

        // when
        val eventBlock = event.toString()

        // then
        assert(eventBlock).isEqualTo(
            """
            $type:
              paths:
                - ${eventPaths[0]}
                - ${eventPaths[1]}
        """.trimIndent()
        )
    }
}