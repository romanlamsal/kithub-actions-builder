//INCLUDE ./src/main/kotlin/BlockElement.kt
//INCLUDE ./src/main/kotlin/On.kt
//INCLUDE ./src/main/kotlin/Job.Kt
//INCLUDE ./src/main/kotlin/Step.kt
//INCLUDE ./src/main/kotlin/Uses.kt
//INCLUDE ./src/main/kotlin/Workflow.kt

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
    }
}.also { println(it) }
