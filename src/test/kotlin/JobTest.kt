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
        assert(throwingToString).thrownError {  }
    }

    @Test
    fun `should have a defaulting checkout action step`() {
        // given
        val name = "build"
        val runsOn = "ubuntuhuhu"

        // when
        val job = Job(name = name, runsOn = runsOn).apply { checkout() }

        // then
        assert(job.toString()).isEqualTo("""
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
        val run = "exit 0"

        // when
        val job = Job(name = jobName, runsOn = runsOn).apply {
            step(stepName) {
                this.run = run
            }
        }

        // then
        assert(job.toString()).isEqualTo(
            """
            $jobName:
              runs-on: $runsOn
              steps:
                - name: $stepName
                  run: $run
        """.trimIndent()
        )
    }
}