class Workflow(
    private val name: String,
) : BlockElement() {
    private val jobs = emptyList<Job>().toMutableList()

    fun job(name: String, c: Job.() -> Unit) = jobs.add(Job(name).apply(c))

    override fun toString(): String {
        val jobBlocks = jobs.map {
            it.toString().indentBlock()
        }

        return """
            name: $name

            jobs:

        """.trimIndent() + jobBlocks.joinToString(separator = "\n")
    }
}

fun workflow(name: String, c: Workflow.() -> Unit): String = Workflow(name).apply(c).toString()
