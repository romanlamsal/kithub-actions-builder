# kithub-actions-builder
DSL written in Kotlin to create GitHub actions.

Could be used to generate github action workflows with the added benefit of type-safety 
and contextual hints, thanks to Kotlin.

## kscript
The library can be used with kscript. 
Simply add ``//DEPS de.lamsal:kithub-actions-builder:1.0.22`` to your .kts file and kscript will be able to pull it.

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
<!--EXAMPLECODE-->
```kotlin
// (1) Extension function used to declare reusable step in the context of Job
// check below to see how it's used .
fun Job.echoAwesome() {
    step("echo something awesome.") {
        run("echo 'something awesome'.")
    }
}

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

    defaults {
        "foobar" to "barbaz"
        run {
            "working-directory" to "scripts"
        }
    }

    env {
        "bibidi" to "babidi"
    }

    job("build-service-1") {
        // if expression for jobs
        ifExpr = "'foo' != 'bar'"

        checkout()

        step("Install dependencies") {
            // if expression for steps
            ifExpr = "true"
            run("yarn install")
        }

        // (1) custom action invocation
        echoAwesome()

        // step without a name
        // contains multiple run commands which are concatenated automatically
        step {
            run("echo \"Hey... how you doin?\"", "echo \";)\"")
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
                // context for convenience. Will surround given VALUE like "${{ VALUE }}".
                "foo" to context("env.bar")
                "bar" to context.secrets("supersecret")

                // special github context which can either be called directly or by constant values
                "baz" to context.github("token")
                "repo" to context.github.repository
            }
        }
    }
}
```

will produce a ``String`` containing the following:
<!--EXAMPLECODE-->
```yaml
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

defaults:
  foobar: barbaz
  run:
    working-directory: scripts

env:
  bibidi: babidi

jobs:
  build-service-1:
    runs-on: ubuntu-latest
    if: 'foo' != 'bar'
    steps:
      - uses: actions/checkout@v2
      - name: Install dependencies
        if: true
        run: yarn install
      - name: echo something awesome.
        run: echo 'something awesome'.
      - run: |
          echo "Hey... how you doin?"
          echo ";)"
      - name: Uses uses with with
        uses: actions/foo@v1
        with:
          foo: bar
      - name: Uses uses without with but with env
        uses: actions/bar@v1env:
          foo: ${{ env.bar }}
          bar: ${{ secrets.supersecret }}
          baz: ${{ github.token }}
          repo: ${{ github.repository }}

```
