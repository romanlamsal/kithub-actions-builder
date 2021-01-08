import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class WorkflowTest {

    @Test
    fun `should print complete workflow`() {
        // given
        val workflowName = "build-service-1"
        val jobName = "build-service-1-job"

        // when
        val fullWorkflow = workflow(workflowName) {
            job(jobName) {
                checkout()
            }
        }

        // then
        assert(fullWorkflow).isEqualTo("""
            name: $workflowName
            
            jobs:
              $jobName:
                runs-on: ubuntu-latest
                steps:
                  - uses: actions/checkout@v1
        """.trimIndent())
    }

}