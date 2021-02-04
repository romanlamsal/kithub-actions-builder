package de.lamsal.kithubactionsbuilder.generation

import de.lamsal.kithubactionsbuilder.context.context
import de.lamsal.kithubactionsbuilder.workflow
import java.io.File

fun gradlew(command: String) = "./gradlew $command"

fun main() {
    workflow("build") {
        on {
            push {
                branches = listOf("main")
                paths = listOf("src/main/**/*")
            }
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
                run(gradlew("publish"))
                env {
                    "version" to "1.0.${context.github.runNumber}"
                    "gh_username" to "GitHub"
                    "gh_token" to context.github.token
                    "bintray_user" to "romanlamsal"
                    "bintray_api_key" to context.secrets("BINTRAY_API_KEY")
                    "bintray_vcs_url" to "https://github.com/${context.github.repository}.git"
                }
            }
        }
    }.also { File("${System.getenv("rootDir")}/.github/workflows/build.yml").writeText(it) }
}
