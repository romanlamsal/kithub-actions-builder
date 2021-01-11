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
            val username = "GitHub"
            val token = "\${{ secrets.GITHUB_TOKEN }}"
            val version = "1.0.\${{ github.run_number }}"
            run(gradlew("publish -Pusername=$username -Ptoken=$token -Pversion=$version"))
        }
    }
}.also { println(it) }
