package de.lamsal.kithubactionsbuilder.context

object GithubContext {
    operator fun invoke(value: String) = context("github.$value")

    val token = invoke("token")
    val repository = invoke("repository")
    val runNumber = invoke("run_number")
}
