//INCLUDE ./src/main/kotlin/de/lamsal/kithubactionsbuilder/BlockElement.kt
//INCLUDE ./src/main/kotlin/de/lamsal/kithubactionsbuilder/On.kt
//INCLUDE ./src/main/kotlin/de/lamsal/kithubactionsbuilder/Job.Kt
//INCLUDE ./src/main/kotlin/de/lamsal/kithubactionsbuilder/Step.kt
//INCLUDE ./src/main/kotlin/de/lamsal/kithubactionsbuilder/Uses.kt
//INCLUDE ./src/main/kotlin/de/lamsal/kithubactionsbuilder/Workflow.kt

fun gradlew(command: String) = "./gradlew $command"

workflow("build") {
    on {
        push()
    }

    job("build") {

        checkout()

        step("Install dependencies") {
            run(gradlew("install"))
        }

        step("Run tests") {
            run(gradlew("test"))
        }

        step("Build library") {
            run(gradlew("build"))
        }

        step("Publish") {
            run(gradlew("publish -Pusername=GitHub -Ptoken=\${{ secrets.GITHUB_TOKEN }}"))
        }
    }
}.also { println(it) }
