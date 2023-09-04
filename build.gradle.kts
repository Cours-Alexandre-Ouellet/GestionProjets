import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.8.20"
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "com.humaininc.gestionprojets"
version = "0.0.2"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.humaininc.gestionprojets.LanceurAppKt")
}

dependencies {
    implementation("org.openjfx:javafx:21-ea+5")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.21")
    implementation("com.mysql:mysql-connector-j:8.0.33")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "17"
}

