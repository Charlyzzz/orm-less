import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.org.fusesource.jansi.AnsiRenderer.test

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "com.10Pines.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.exposed:exposed:0.12.2")
    implementation("com.h2database:h2:1.4.198")
    implementation("ch.qos.logback:logback-classic:1.2.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.1.51")
    testImplementation("junit:junit:4.12")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}