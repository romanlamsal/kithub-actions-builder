package de.lamsal.kithubactionsbuilder.context

object Context {
    // Initialized lazily due to some inconsistency in loading kotlin's object classes
    val github: GithubContext by lazy { GithubContext }

    operator fun invoke(value: String) = "\${{ $value }}"
    fun secrets(value: String) = invoke("secrets.$value")
}

val context = Context
