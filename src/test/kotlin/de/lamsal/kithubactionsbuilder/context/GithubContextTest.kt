package de.lamsal.kithubactionsbuilder.context

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class GithubContextTest {

    @Test
    fun `should interpolate and prepend 'github' to given value`() {
        // given
        val value = "something"

        // when
        val interpolated = GithubContext(value)

        // then
        assert(interpolated).isEqualTo("\${{ github.$value }}")
    }

    @TestFactory
    fun testValues() = listOf(
        GithubContext.token to "token",
        GithubContext.repository to "repository",
        GithubContext.runNumber to "run_number",
    ).map { (contextValue, expected) ->
        DynamicTest.dynamicTest("should interpolate GithubContext#$expected to \${{ github.$expected }}") {
            // given
            // args

            // when
            // contextValue

            // then
            assert(contextValue).isEqualTo("\${{ github.$expected }}")
        }
    }
}