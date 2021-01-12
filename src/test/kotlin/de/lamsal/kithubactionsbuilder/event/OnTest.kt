package de.lamsal.kithubactionsbuilder.event

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class OnTest {

    companion object {
        private const val eventBlock = "foo:\n  - bar"
    }

    @Test
    fun `should return multiple events with correct indentation`() {
        // given
        val on = On().apply {
            events.add(MockWorkflowEvent())
            events.add(MockWorkflowEvent())
        }

        // when
        val onBlock = on.toString()

        // then
        assert(onBlock).isEqualTo("""
            on:
              foo:
                - bar
              foo:
                - bar
        """.trimIndent())
    }

    @Test
    fun `should add CodeEvent('push') on calling push`() {
        // given
        val on = On()

        // when
        on.apply {
            push()

            // then
            assert((events[0] as CodeEvent).type).isEqualTo("push")
        }
    }

    @Test
    fun `should add CodeEvent('pull_request') on calling pullRequest`() {
        // given
        val on = On()

        // when
        on.apply {
            pullRequest()

            // then
            assert((events[0] as CodeEvent).type).isEqualTo("pull_request")
        }
    }

    private inner class MockWorkflowEvent : WorkflowEvent("") {
        override fun toString(): String = eventBlock
    }
}