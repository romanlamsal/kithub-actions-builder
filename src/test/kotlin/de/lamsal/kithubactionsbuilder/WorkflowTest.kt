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
        val runsOn = "haselnuss"

        // when
        val fullWorkflow = workflow(workflowName) {
            on {
                push()
            }

            job(jobName) {
                this.runsOn = runsOn
                checkout()
            }
        }

        // then
        assert(fullWorkflow).isEqualTo("""
            name: $workflowName
            
            on:
              push:
            
            jobs:
              $jobName:
                runs-on: $runsOn
                steps:
                  - uses: actions/checkout@v2
        """.trimIndent())
    }

    @Test
    fun `should print complete workflow including envvars`() {
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
            
            on:
              push:
            
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

    @Test
    fun `should print complete workflow including envsvars, defaults`() {
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

            defaults {
                "foobar" to "barbaz"
                run {
                    "working-directory" to "scripts"
                }
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
            
            on:
              push:
            
            defaults:
              foobar: barbaz
              run:
                working-directory: scripts
            
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

    @Test
    fun `should print complete workflow including envsvars, run defaults only`() {
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

            defaults {
                run {
                    "working-directory" to "scripts"
                }
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
            
            on:
              push:
            
            defaults:
              run:
                working-directory: scripts
            
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