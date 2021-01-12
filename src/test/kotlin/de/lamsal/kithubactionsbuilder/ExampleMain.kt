package de.lamsal.kithubactionsbuilder

// Extension function used to declare reusable step in the context of Job
// check below to see how it's used.
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

            echoAwesome()

            // step without a no name
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
                    "foo" to "bar"
                }
            }
        }
    }.also { println(it) }
}
