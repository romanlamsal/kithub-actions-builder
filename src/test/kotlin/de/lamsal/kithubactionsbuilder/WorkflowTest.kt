package de.lamsal.kithubactionsbuilder

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class WorkflowTest {

    @Test
    fun `should print complete workflow without env`() {
        // given
        val workflowName = "build-service-1"
        val jobName = "build-service-1-job"

        // when
        val fullWorkflow = workflow(workflowName) {
            on {
                push()
            }

            job(jobName) {
                checkout()
            }
        }

        // then
        assert(fullWorkflow).isEqualTo("""
            name: $workflowName
            
            on: push
            
            jobs:
              $jobName:
                runs-on: ubuntu-latest
                steps:
                  - uses: actions/checkout@v1
        """.trimIndent())
    }


    @Test
    fun `should print complete workflow including envs`() {
        // given
        val workflowName = "build-service-1"
        val jobName = "build-service-1-job"

        // when
        val fullWorkflow = workflow(workflowName) {
            on {
                push()
            }

            env {
                "foo" to "bar"
            }

            job(jobName) {
                step {
                    uses("actions/checkout@v1") {
                        "bar" to "baz"
                    }
                }

                step {
                    run("exit 0")
                    env {
                        "lorem" to "ipsum"
                    }
                }
            }
        }

        // then
        assert(fullWorkflow).isEqualTo("""
            name: $workflowName
            
            on: push
            
            env:
              foo: bar
            
            jobs:
              $jobName:
                runs-on: ubuntu-latest
                steps:
                  - uses: actions/checkout@v1
                    with:
                      bar: baz
                  - run: exit 0
                    env:
                      lorem: ipsum
        """.trimIndent())
    }
}