import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion = "2.2.1.RELEASE"
val junitVersion = "5.5.2"
val kluentVersion = "1.56"
val mockkVersion = "1.9.3"




plugins {
    kotlin("jvm") version "1.3.60"
    kotlin("plugin.spring") version "1.3.60"
    id("org.springframework.boot") version "2.2.1.RELEASE"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}
dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.amshove.kluent:kluent:$kluentVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
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