import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class StepTest {

    @Test
    fun `should return step with only 'run'`() {
        // given
        val run = "echo 'so great'"

        // when
        val step = Step()
        step.run = run

        // then
        assert(step.toString()).isEqualTo("- run: $run")
    }

    @Test
    fun `should return step with name`() {
        // given
        val name = "awesome-name"

        // when
        val step = Step(name = name)

        // then
        assert(step.toString()).isEqualTo("- name: $name")
    }

    @Test
    fun `should correctly indent name with run`() {
        // given
        val name = "awesome-name"
        val run = "echo 'so great'"

        // when
        val step = Step(name = name).apply { this.run = run }

        // then
        assert(step.toString()).isEqualTo(
            """
            - name: $name
              run: $run
        """.trimIndent()
        )
    }

    @Test
    fun `should attach Uses element`() {
        // given
        val usesName = "actions/awesome@v1"
        val keyValuePair = "foo" to "bar"

        // when
        val step = Step().apply {
            uses(usesName) {
                keyValuePair.first being keyValuePair.second
            }
        }

        // then
        assert(step.toString()).isEqualTo(
            """
            - uses: $usesName
              with:
                ${keyValuePair.first}: ${keyValuePair.second}
        """.trimIndent()
        )
    }

    @Test
    fun `should attach Uses element when name is given`() {
        // given
        val stepName = "awesome-name"
        val usesName = "actions/awesome@v1"
        val keyValuePair = "foo" to "bar"

        // when
        val step = Step(name = stepName).apply {
            uses(usesName) {
                keyValuePair.first being keyValuePair.second
            }
        }

        // then
        assert(step.toString()).isEqualTo(
            """
            - name: $stepName
              uses: $usesName
              with:
                ${keyValuePair.first}: ${keyValuePair.second}
        """.trimIndent()
        )
    }
}