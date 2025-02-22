/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.10.2/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)

    // This is for the Java Discord Bot
    implementation("net.dv8tion:JDA:5.2.2")

    // This is for reading dotenv file
    implementation("io.github.cdimascio:dotenv-java:3.1.0")

    // https://mvnrepository.com/artifact/org.postgresql/postgresql
    implementation("org.postgresql:postgresql:42.6.0")

}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "net.snacj.MyBot"
}

tasks.shadowJar {
    archiveBaseName.set("CryoBot") // Name of the JAR file
    archiveVersion.set("1.0")        // Version
    archiveClassifier.set("")        // Removes the "all" classifier
    mergeServiceFiles()              // Handles merging service files (like META-INF/services)
    from(".") {
        include(".env")
    }

}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
