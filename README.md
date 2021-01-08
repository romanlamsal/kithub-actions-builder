# kithub-actions-builder
DSL written in Kotlin to create GitHub actions.

Could be used to generate github action workflows with the added benefit of type-safety 
and contextual hints, thanks to Kotlin.

## Example
The following piece of code:
```kotlin
workflow("build-service-1") {
    job("build-service-1") {

        checkout()

        step("Install dependencies") {
            run = "yarn install"
        }

        step("Build javascript") {
            run = "yarn build"
        }

        step("Build docker") {
            run = "docker build -t something:else ."
        }

        step("Push docker") {
            run = "docker push something:else"
        }

        // has no name
        step {
            run = """
                echo "hallo wie geht's?"
                echo "danke gut."
            """.trimIndent()
        }

        step("Uses uses with with") {
            uses("actions/foo@v1") {
                // add 'with' declaration by adding key-value-pairs like so
                "foo" being "bar"
            }
        }

        step("Uses uses with with") {
            uses("actions/bar@v1")
        }
    }
}
```

will produce a ``String`` containing the following:

````yaml
name: build-service-1

jobs:
  build-service-1:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v1
      - name: Install dependencies
        run: yarn install
      - name: Build javascript
        run: yarn build
      - name: Build docker
        run: docker build -t something:else .
      - name: Push docker
        run: docker push something:else
      - run: |
          echo "hallo wie geht's?"
          echo "danke gut."
      - name: Uses uses with with
        uses: actions/foo@v1
        with:
          foo: bar
      - name: Uses uses with with
        uses: actions/bar@v1
````
