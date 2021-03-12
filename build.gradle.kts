import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    application
    maven
    `maven-publish`
    id("jacoco")
}

group = "de.lamsal"
version = System.getenv("version") ?: "1.0"

// apply(from = "readme.gradle.kts")

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.21")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("com.willowtreeapps.assertk:assertk:0.10")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

sourceSets {
    create("generation") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/romanlamsal/kithub-actions-builder")
            credentials {
                username = System.getenv("gh_username")
                password = System.getenv("gh_token")
            }
        }
        maven {
            name = "bintray"
            val user = System.getenv("bintray_user")
            val key = System.getenv("bintray_api_key")
            val repoName = "maven"
            val packageName = "kithub-actions-builder"
            setUrl(
                "https://api.bintray.com/maven/" +
                        "$user/$repoName/$packageName/;" +
                        "publish=1"
            )
            credentials {
                username = user
                password = key
            }
        }
    }
    publications {
        register("gpr", MavenPublication::class.java) {
            from(components["java"])
        }
    }
}

fun JacocoReportsContainer.reports() {
    xml.isEnabled = true
    html.isEnabled = true
    xml.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    html.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/html")
}

tasks.register<JavaExec>("createWorkflows") {
    main = "de.lamsal.kithubactionsbuilder.generation.BuildKt"
    classpath = sourceSets["generation"].runtimeClasspath
    environment("rootDir", rootDir)
    group = "workflow"
}

tasks.register("extractExampleCode") {
    group = "documentation"

    val exampleCode = File("$rootDir/src/generation/kotlin/de/lamsal/kithubactionsbuilder/generation/readmeExample.kt")
        .readText().let {
            "//README-CODE(.*)//README-CODE"
                .toRegex(setOf(RegexOption.MULTILINE, RegexOption.COMMENTS, RegexOption.DOT_MATCHES_ALL))
                .find(it)!!.groupValues[1].trimIndent()
        }
    File("$rootDir/examples/readmeExampleCode.kts").writeText(exampleCode)
}

tasks.register<JavaExec>("executeExampleCode") {
    group = "documentation"
    main = "de.lamsal.kithubactionsbuilder.generation.ReadmeExampleKt"
    classpath = sourceSets["generation"].runtimeClasspath
    environment("rootDir", rootDir)
}

tasks.register("updateReadme") {
    group = "documentation"
    dependsOn(":extractExampleCode")
    dependsOn(":executeExampleCode")

    doLast {
        File("$rootDir/README.md").apply {
            val content = readText().let { content ->
                fun replaceExampleCode(fileLoc: String, type: String): (String) -> String {
                    return {
                        val replacement = File(fileLoc).readText()
                        "<!--EXAMPLECODE-->\n```$type([^```]*)```"
                            .toRegex(setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL))
                            .replace(it) {
                                "<!--EXAMPLECODE-->\n```$type\n$replacement\n```"
                            }
                    }
                }

                listOf(
                    replaceExampleCode("$rootDir/examples/readmeExampleCode.kts", "kotlin"),
                    replaceExampleCode("$rootDir/examples/readmeExampleOutput.yml", "yaml")
                ).fold(initial = content) { acc, it -> it(acc) }
            }

            writeText(content)
        }
    }
}

tasks.register("update") {
    group = "verification"
    dependsOn("updateReadme")
    dependsOn("createWorkflows")
}
