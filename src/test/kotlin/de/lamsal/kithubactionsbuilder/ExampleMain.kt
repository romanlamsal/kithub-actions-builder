package de.lamsal.kithubactionsbuilder

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

            // has no name
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
