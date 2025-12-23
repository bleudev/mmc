import java.nio.file.Files
import java.nio.file.Path

plugins {
    kotlin("jvm") version "2.3.0"
    id("fabric-loom") version "1.14-SNAPSHOT" apply false
    id("maven-publish")
    id("com.modrinth.minotaur") version "2.+" apply false
}

repositories {
    mavenCentral()
}

fun configure(v: String, maxExv: String) {
    var changelog: String = Files.readString(Path.of(uri(project.file("CHANGELOG.md").getAbsolutePath())))
    project(":$v") {
        extensions.extraProperties.apply {
            set("changelog", changelog)
            set("minecraft_version", v)
            set("max_exc_version", maxExv)
            set("mod_version", "${project.findProperty("general_version")}+$v")
        }
    }
}

configure("1.21", "1.21.9")
configure("1.21.9", "1.21.12")