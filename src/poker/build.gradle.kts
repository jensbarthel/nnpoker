import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junitVersion = "5.5.2"
val kluentVersion = "1.56"

plugins {
    kotlin("jvm") version "1.3.50"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}
dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.amshove.kluent:kluent:$kluentVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "12"
}