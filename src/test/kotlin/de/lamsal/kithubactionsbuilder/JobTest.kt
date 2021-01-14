package de.lamsal.kithubactionsbuilder

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class JobTest {

    @Test
    fun `should throw error on job without any steps`() {
        // given
        val name = "build"
        val runsOn = "ubuntuhuhu"

        // when
        val throwingToString = { Job(name = name, runsOn = runsOn).toString() }

        // then
        assert(throwingToString).thrownError { }
    }

    @Test
    fun `should have checkout action step`() {
        // given
        val name = "build"
        val runsOn = "ubuntuhuhu"

        // when
        val job = Job(name = name, runsOn = runsOn).apply { checkout() }

        // then
        assert(job.toString()).isEqualTo(
            """
            $name:
              runs-on: $runsOn
              steps:
                - uses: actions/checkout@v1
            """.trimIndent()
        )
    }

    @Test
    fun `should return job with step`() {
        // given
        val jobName = "build"
        val runsOn = "ubuntuhuhu"
        val stepName = "install"
        val runCommand = "exit 0"

        // when
        val job = Job(name = jobName, runsOn = runsOn).apply {
            step(stepName) {
                run(runCommand)
            }
        }

        // then
        assert(job.toString()).isEqualTo(
            """
            $jobName:
              runs-on: $runsOn
              steps:
                - name: $stepName
                  run: $runCommand
            """.trimIndent()
        )
    }

    @Test
    fun `should return 'ubuntu-latest' as default runsOn`() {
        // given
        val jobName = "build"
        val stepName = "install"
        val runCommand = "exit 0"

        // when
        val job = Job(name = jobName).apply {
            step(stepName) {
                run(runCommand)
            }
        }

        // then
        assert(job.toString()).isEqualTo(
            """
            $jobName:
              runs-on: ubuntu-latest
              steps:
                - name: $stepName
                  run: $runCommand
            """.trimIndent()
        )
    }

    @Test
    fun `should return job with step and if`() {
        // given
        val jobName = "build"
        val runsOn = "ubuntuhuhu"
        val stepName = "install"
        val runCommand = "exit 0"
        val ifExpr = "1 == 0"

        // when
        val job = Job(name = jobName, runsOn = runsOn).apply {
            this.ifExpr = ifExpr
            step(stepName) {
                run(runCommand)
            }
        }

        // then
        assert(job.toString()).isEqualTo(
            """
            $jobName:
              runs-on: $runsOn
              if: $ifExpr
              steps:
                - name: $stepName
                  run: $runCommand
            """.trimIndent()
        )
    }
}