package de.lamsal.kithubactionsbuilder

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class StepTest {

    @Test
    fun `should return step with only 'run'`() {
        // given
        val run = "echo 'so great'"

        // when
        val step = Step()
        step.runCommands.add(run)

        // then
        assert(step.toString()).isEqualTo("- run: $run")
    }

    @Test
    fun `should throw when step has neither 'uses' nor 'run' return step with name`() {
        // given
        val name = "awesome-name"

        // when
        val throwingToString = { Step(name = name).toString() }

        // then
        assert(throwingToString).thrownError { }
    }

    @Test
    fun `should return step with name and single-line run`() {
        // given
        val name = "awesome-name"
        val runCommand = "exit 0"

        // when
        val step = Step(name = name).apply { run(runCommand) }

        // then
        assert(step.toString()).isEqualTo(
            """
            - name: $name
              run: $runCommand
        """.trimIndent()
        )
    }

    @Test
    fun `should correctly split multiline command`() {
        // given
        val runCommands = listOf("exit 0", "exit 1")

        // when
        val step = Step().apply { run(runCommands.joinToString(separator = "\n")) }

        // then
        assert(step.toString()).isEqualTo(
            """
            - run: |
                ${runCommands[0]}
                ${runCommands[1]}
        """.trimIndent()
        )
    }

    @Test
    fun `should correctly split multiple single-line commands`() {
        // given
        val runCommands = listOf("exit 0", "exit 1")

        // when
        val step = Step().apply { run(*runCommands.toTypedArray()) }

        // then
        assert(step.toString()).isEqualTo(
            """
            - run: |
                ${runCommands[0]}
                ${runCommands[1]}
        """.trimIndent()
        )
    }

    @Test
    fun `should correctly split multiple single- and multi-line commands`() {
        // given
        val runCommands = listOf("exit 0", "exit 1\nexit 2")

        // when
        val step = Step().apply { run(*runCommands.toTypedArray()) }

        // then
        assert(step.toString()).isEqualTo(
            """
            - run: |
                exit 0
                exit 1
                exit 2
        """.trimIndent()
        )
    }

    @Test
    fun `should correctly indent name with run`() {
        // given
        val name = "awesome-name"
        val run = "echo 'so great'"

        // when
        val step = Step(name = name).apply { this.runCommands.add(run) }

        // then
        assert(step.toString()).isEqualTo(
            """
            - name: $name
              run: $run
        """.trimIndent()
        )
    }

    @Test
    fun `should attach Uses element`() {
        // given
        val usesName = "actions/awesome@v1"
        val keyValuePair = "foo" to "bar"

        // when
        val step = Step().apply {
            uses(usesName) {
                keyValuePair.first to keyValuePair.second
            }
        }

        // then
        assert(step.toString()).isEqualTo(
            """
            - uses: $usesName
              with:
                ${keyValuePair.first}: ${keyValuePair.second}
        """.trimIndent()
        )
    }

    @Test
    fun `should attach Uses element when name is given`() {
        // given
        val stepName = "awesome-name"
        val usesName = "actions/awesome@v1"
        val keyValuePair = "foo" to "bar"

        // when
        val step = Step(name = stepName).apply {
            uses(usesName) {
                keyValuePair.first to keyValuePair.second
            }
        }

        // then
        assert(step.toString()).isEqualTo(
            """
            - name: $stepName
              uses: $usesName
              with:
                ${keyValuePair.first}: ${keyValuePair.second}
        """.trimIndent()
        )
    }

    @Test
    fun `should return step with 'run' in addition to 'env'`() {
        // given
        val run = "echo 'so great'"
        val envVar = "foo" to "bar"

        // when
        val step = Step().apply {
            runCommands.add(run)
            env {
                envVar.first to envVar.second
            }
        }

        // then
        assert(step.toString()).isEqualTo(
            """
            - run: $run
              env:
                ${envVar.first}: ${envVar.second}
            """.trimIndent()
        )
    }
}
