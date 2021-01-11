//INCLUDE ./src/main/kotlin/Workflow.kt
//INCLUDE ./src/main/kotlin/BlockElement.kt
//INCLUDE ./src/main/kotlin/Job.Kt
//INCLUDE ./src/main/kotlin/Step.kt
//INCLUDE ./src/main/kotlin/Uses.kt

fun gradlew(command: String) = "./gradlew $command"

workflow("build-service-1") {
    job("build-service-1") {

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
    }
}.also { println(it) }
