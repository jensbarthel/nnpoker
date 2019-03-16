import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junitVersion = "5.4.0"
val springBootWebVersion = "2.1.3.RELEASE"


plugins {
    kotlin("jvm") version "1.3.21"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}