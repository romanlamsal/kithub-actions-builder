fun main() {
    workflow("build-service-1") {
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
                    "foo" being "bar"
                }
            }

            step("Uses uses with with") {
                uses("actions/bar@v1")
            }
        }
    }.also { println(it) }
}
