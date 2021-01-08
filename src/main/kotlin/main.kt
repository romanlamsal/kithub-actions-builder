/*
val foo = workflow("build-service-1") { workflow ->
    job("build-service-1") {

        checkout()

        step("Install dependencies") {
            run = "yarn install"
        }

        step("Build javascript") {
            run = "yarn build"
        }

        step("Build docker") {
            run = "docker build -t something:else ."
        }

        step("Push docker") {
            run = "docker push something:else"
        }

        // has no name
        step {
            run = """
                echo "hallo wie geht's?"
                echo "danke gut."
            """.trimIndent()
        }

        step("Uses uses with with") {
            uses("actions/foo@v1") {
                put("foo", "bar")
            }
        }

        step("Uses uses with with") {
            uses("actions/bar@v1")
        }
    }
}
*/
