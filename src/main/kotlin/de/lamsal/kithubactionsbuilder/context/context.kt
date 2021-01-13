package de.lamsal.kithubactionsbuilder.context

object Context {
    operator fun invoke(value: String) = "\${{ $value }}"
    fun github(value: String) = invoke("github.$value")
    fun secrets(value: String) = invoke("secrets.$value")
}

val context = Context
