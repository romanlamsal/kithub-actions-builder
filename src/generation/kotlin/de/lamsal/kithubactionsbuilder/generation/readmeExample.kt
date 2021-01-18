package de.lamsal.kithubactionsbuilder.generation

import de.lamsal.kithubactionsbuilder.Job
import de.lamsal.kithubactionsbuilder.context.context
import de.lamsal.kithubactionsbuilder.workflow


fun main() {
    //README-CODE
    // (1) Extension function used to declare reusable step in the context of Job
    // check below to see how it's used .
    fun Job.echoAwesome() {
        step("echo something awesome.") {
            run("echo 'something awesome'.")
        }
    }

    workflow("build-service-1") {
        on {
            push {
                branches = listOf("foo", "bar")
                paths = listOf("baz", "foobar")
            }
            pullRequest {
                branches = listOf("lorem", "ipsum")
                paths = listOf("dolor", "sit amt")
            }
        }

        defaults {
            "foobar" to "barbaz"
            run {
                "working-directory" to "scripts"
            }
        }

        env {
            "bibidi" to "babidi"
        }

        job("build-service-1") {
            // if expression for jobs
            ifExpr = "'foo' != 'bar'"

            checkout()

            step("Install dependencies") {
                // if expression for steps
                ifExpr = "true"
                run("yarn install")
            }

            // (1) custom action invocation
            echoAwesome()

            // step without a name
            // contains multiple run commands which are concatenated automatically
            step {
                run("echo \"Hey... how you doin?\"", "echo \";)\"")
            }

            step("Uses uses with with") {
                uses("actions/foo@v1") {
                    // add 'with' declaration by adding key-value-pairs like so
                    "foo" to "bar"
                }
            }

            step("Uses uses without with but with env") {
                uses("actions/bar@v1")
                env {
                    // context for convenience. Will surround given VALUE like "${{ VALUE }}".
                    "foo" to context("env.bar")
                    "bar" to context.secrets("supersecret")

                    // special github context which can either be called directly or by constant values
                    "baz" to context.github("token")
                    "repo" to context.github.repository
                }
            }
        }
    }//README-CODE
        .also { println(it) }
}
