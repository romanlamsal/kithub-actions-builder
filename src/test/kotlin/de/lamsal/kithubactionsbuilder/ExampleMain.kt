package de.lamsal.kithubactionsbuilder

import de.lamsal.kithubactionsbuilder.context.context

// (1) Extension function used to declare reusable step in the context of Job
// check below to see how it's used .
fun Job.echoAwesome() = apply {
    step("echo something awesome") {
        run("echo 'something awesome'.")
    }
}

fun main() {
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

        env {
            "bibidi" to "babidi"
        }

        job("build-service-1") {

            checkout()

            step("Install dependencies") {
                run("yarn install")
            }

            // (1) custom action invocation
            echoAwesome()

            // step without a name
            // contains multiple run commands which are concatenated automatically
            step {
                run("""echo "hallo wie geht's?"""", """echo "danke gut."""")
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
    }.also { println(it) }
}
