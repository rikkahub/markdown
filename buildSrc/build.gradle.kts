plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://cache-redirector.jetbrains.com/plugins.gradle.org/m2")
    maven("https://cache-redirector.jetbrains.com/repo1.maven.org/maven2")
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.6")
}
