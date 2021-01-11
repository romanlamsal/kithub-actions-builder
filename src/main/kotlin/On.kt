class On : BlockElement() {
    lateinit var event: String

    fun push() { event = "push" }

    override fun toString(): String {
        return "on: $event"
    }
}
