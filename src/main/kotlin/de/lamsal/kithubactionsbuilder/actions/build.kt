package de.lamsal.kithubactionsbuilder.actions

import de.lamsal.kithubactionsbuilder.workflow

fun main() {
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
                run(gradlew("publish"))
                env {
                    "version" to "1.0.\${{ github.run_number }}"
                    "gh_username" to "GitHub"
                    "gh_token" to "\${{ github.token }}"
                }
            }
        }
    }.also { println(it) }
}
