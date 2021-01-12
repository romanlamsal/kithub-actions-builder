# kithub-actions-builder
DSL written in Kotlin to create GitHub actions.

Could be used to generate github action workflows with the added benefit of type-safety 
and contextual hints, thanks to Kotlin.

## Example
The following piece of code:
```kotlin
workflow("build-service-1") {
    on {
        push()
    }

    env {
        "bibidi" to "babidi"
    }

    job("build-service-1") {

        checkout()

        step("Install dependencies") {
            run("yarn install")
        }

        // has no name
        step {
            run("""echo "hallo wie geht's?"""", """echo "danke gut."""")
        }

        step("Uses uses with with") {
            uses("actions/foo@v1") {
                // add 'with' declaration by adding key-value-pairs like so
                "foo" to "bar"
            }
        }

        step("Uses uses without with but with env") {
            uses("actions/bar@v1")
            env {
                "foo" to "bar"
            }
        }
    }
}
```

will produce a ``String`` containing the following:

````yaml
name: build-service-1

on: push

env:
  bibidi: babidi

jobs:
  build-service-1:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v1
      - name: Install dependencies
        run: yarn install
      - run: |
          echo "hallo wie geht's?"
          echo "danke gut."
      - name: Uses uses with with
        uses: actions/foo@v1
        with:
          foo: bar
      - name: Uses uses without with but with env
        uses: actions/bar@v1
        env:
          foo: bar
````
