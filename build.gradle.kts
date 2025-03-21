import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.time.Instant
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR

plugins {
    id("application")
    id("checkstyle")
    id("codenarc")
    id("groovy")
    id("jacoco")
    id("java")
    id("java-test-fixtures")
    id("maven-publish")
    id("pmd")

    alias(libs.plugins.conventionalCommits)
    alias(libs.plugins.dependencyCheck)
    alias(libs.plugins.dependencyUpdates)
    alias(libs.plugins.errorprone)
    alias(libs.plugins.gitProperties)
    alias(libs.plugins.lombok)
    alias(libs.plugins.release)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.spotless)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencyManagement)
    alias(libs.plugins.testLogger)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
}

group = "com.github.marcindabrowski.example"
version = scmVersion.version

repositories {
    mavenCentral()
}

// overriding Spring managed version
ext["groovy.version"] = libs.versions.groovy.get()

configurations.all {
    // We are not using WebSockets
    exclude(group = "io.undertow", module = "undertow-websockets-jsr")
    // We are using Undertow
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
}

dependencies {
    errorprone(libs.errorprone.core)

    annotationProcessor(libs.spring.boot.configurationProcessor)

    implementation(libs.spring.boot.starter.undertow)
    implementation(libs.spring.boot.starter.web)

    developmentOnly(libs.spring.boot.devtools)

    testFixturesApi(libs.bomDependency.spring.web)
    testFixturesImplementation(platform(libs.groovy.platform))
    testFixturesImplementation(libs.bomDependency.groovy.core)

    testImplementation(platform(libs.test.junit.platform))
    testImplementation(platform(libs.groovy.platform))
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.test.equalsVerifier)
    testImplementation(libs.test.spock.core)

//    integrationImplementation(libs.test.spock.spring)
}

application {
    mainClass = "com.github.marcindabrowski.example.roomsbooking.RoomsBookingApplication"
}

checkstyle {
    configDirectory.set(rootProject.file("gradle/config/checkstyle"))
    configFile = rootProject.file("gradle/config/checkstyle/google_checks.xml")
    toolVersion = libs.versions.checkstyle.get()
}

codenarc {
    configFile = rootProject.file("gradle/config/codenarc/CodenarcRuleSet.groovy")
    reportFormat = "console"
    toolVersion = libs.versions.codenarc.get()
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

publishing {
    publications {
        create<MavenPublication>(rootProject.name) {
            from(components["java"])

            pom {
                name = "Example rooms booking system"
                description = "Service with example REST API for room booking system."
                url = "https://github.com/marcindabrowski/${rootProject.name}"

                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "Marcin"
                        name = "Dąbrowski"
                        timezone = "Europe/Warsaw"
                    }
                }
                scm {
                    connection = "scm:git:git@github.com:marcindabrowski/${rootProject.name}.git"
                    developerConnection = "scm:git:git@github.com:marcindabrowski/${rootProject.name}.git"
                    url = "https://github.com/marcindabrowski/${rootProject.name}"
                }
            }
        }
    }
    repositories {
        maven {
            name = "ProjectGitHubPackages"
            url = uri("https://maven.pkg.github.com/marcindabrowski/${rootProject.name}")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

pmd {
    isConsoleOutput = true
    isIgnoreFailures = false
    ruleSets = listOf(
        "category/java/bestpractices.xml",
        "category/java/errorprone.xml",
        "category/java/performance.xml",
        "category/java/security.xml",
    )
    toolVersion = libs.versions.pmd.get()
}

sonarqube {
    properties {
        property("sonar.coverage.jacoco.xmlReportPaths", "${project.layout.buildDirectory.get()}/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.java.pmd.reportPaths", "${project.layout.buildDirectory.get()}/reports/pmd/main.xml")
        property("sonar.organization", "marcindabrowski")
        property("sonar.projectKey", "marcindabrowski_examples-rooms-booking")
    }
}

spotless {
    java {
        googleJavaFormat(libs.versions.googleJavaFormat.get()).reflowLongStrings()
        targetExclude("build/generated/", "src/generated/")
    }
    freshmark {
        target("*.md")
    }
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        val test by getting(JvmTestSuite::class)

        register<JvmTestSuite>("integration") {
            dependencies {
                implementation(project())
                implementation(libs.spring.boot.starter.test)
                implementation(libs.test.spock.core)
                implementation(libs.test.spock.spring)
                implementation(testFixtures(project()))
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }

        configureEach {
            if (this is JvmTestSuite) {
                useJUnitJupiter(libs.versions.junit)

                dependencies {
                    implementation(platform(libs.groovy.platform))
                    implementation(libs.test.spock.core)
                }

                targets {
                    all {
                        testTask.configure {
                            finalizedBy(tasks.jacocoTestReport)
                            jvmArgs("-XX:+EnableDynamicAgentLoading")

                            maxParallelForks = 1

                            testLogging {
                                exceptionFormat = FULL
                                events = setOf(FAILED, PASSED, SKIPPED, STANDARD_ERROR)
                            }
                        }
                    }
                }
            }
        }
    }
}

tasks.withType<DependencyUpdatesTask>().configureEach {
    // disallow release candidates as upgradable versions from stable versions
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
}

tasks.withType<JacocoReport>().configureEach {
    executionData = fileTree("${project.layout.buildDirectory.get()}/jacoco/")
    reports {
        csv.required = false
        html.required = true
        xml.required = true
    }
}

tasks.withType<Jar>().configureEach {
    val jdkLauncherMetadata = javaToolchains.launcherFor {
        implementation.set(java.toolchain.implementation.get())
        languageVersion.set(java.toolchain.languageVersion.get())
        vendor.set(java.toolchain.vendor.get())
    }.get().metadata
    val javaCompileTask = tasks.withType<JavaCompile>().getByName("compileJava")
    val releaseJavaVersion = javaCompileTask.options.release.getOrElse(java.toolchain.languageVersion.get().asInt())
    val projectVersion = org.gradle.util.internal.VersionNumber.parse(project.version.toString())

    manifest {
        attributes(
            "Build-Jdk"              to "${jdkLauncherMetadata.jvmVersion} (${jdkLauncherMetadata.vendor})",
            "Build-Jdk-Spec"         to releaseJavaVersion,
            "Build-OS"               to "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${System.getProperty("os.version")}",
            "Build-Timestamp"        to Instant.now().toString(),
            "Created-By"             to "Gradle ${gradle.gradleVersion}",
            "Implementation-Title"   to project.name,
            "Implementation-URL"     to "https://github.com/marcindabrowski/${rootProject.name}",
            "Implementation-Vendor"  to "Marcin Dąbrowski",
            "Implementation-Version" to project.version,
            "Specification-Title"    to project.name,
            "Specification-Vendor"   to "Marcin Dąbrowski",
            "Specification-Version"  to "${projectVersion.major}.${projectVersion.minor}",
        )
    }
}

tasks.jacocoTestReport {
    sourceSets(sourceSets.main.get(), sourceSets.test.get(), sourceSets.getByName("integration"))
}

tasks.check {
    @Suppress("UnstableApiUsage")
    dependsOn(testing.suites.named("integration"))
}

tasks.register<Test>("allTests") {
    group = "Verification"
    description = "Runs all tests."
    @Suppress("UnstableApiUsage")
    finalizedBy("test", testing.suites.named("integration"))
    enabled = false
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
