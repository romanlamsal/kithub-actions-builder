package de.lamsal.kithubactionsbuilder.actions

import de.lamsal.kithubactionsbuilder.workflow

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
                    "version" to "1.0.\${{ github.run_number }}"
                    "gh_username" to "GitHub"
                    "gh_token" to "\${{ github.token }}"
                    "bintray_user" to "romanlamsal"
                    "bintray_api_key" to "\${{ secrets.BINTRAY_API_KEY }}"
                    "bintray_vcs_url" to "https://github.com/\${{ github.repository }}.git"
                }
            }
        }
    }.also { println(it) }
}
