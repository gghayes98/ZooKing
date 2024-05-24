plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven ("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.varabyte.kotter:kotter-jvm:1.1.2")
    testImplementation("com.varabyte.kotter:kotter-test-support-jvm:1.1.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}