# kithub-actions-builder
DSL written in Kotlin to create GitHub actions.

Could be used to generate github action workflows with the added benefit of type-safety 
and contextual hints, thanks to Kotlin.

## Installation
This package is available via [jcenter](https://bintray.com/romanlamsal/maven/kithub-actions-builder) or 
[github packages](https://github.com/romanlamsal/kithub-actions-builder/packages) 
(needs a private access token/github token - packages are not "public" in the sense that the can
be used out of the box).

### jcenter
````kotlin
repositories {
	jcenter()
}

dependencies {
	implementation("de.lamsal:kithub-actions-builder:1.0.+")
}
````

### github packages
````kotlin
repositories {
	maven {
		name = "GitHubPackages"
		setUrl("https://maven.pkg.github.com/romanlamsal/kithub-actions-builder")
		credentials {
			username = "USERNAME" // contains your github username
			password = "PRIVATE_ACCESS_TOKEN" // your private access token/github token (must be able to read:package)
		}
	}
}

dependencies {
	implementation("de.lamsal:kithub-actions-builder:1.0.21")
}
````

## Example
The following piece of code contains everything the library currently is capable of.
```kotlin
workflow("build-service-1") {
    on {
        push {
            branches = listOf("foo", "bar")
            paths = listOf("baz", "foobar")
        }
        pullRequest {
            branches = listOf("lorem", "ipsum")
            paths = listOf("dolor", "sit amt")
        }
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

on:
  push:
    branches:
      - foo
      - bar
    paths:
      - baz
      - foobar
  pull_request:
    branches:
      - lorem
      - ipsum
    paths:
      - dolor
      - sit amt

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
