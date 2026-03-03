import java.nio.file.Files
import java.nio.file.Path

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.serialization") version "2.3.0"
    id("net.fabricmc.fabric-loom") version "1.15.4" apply false
    id("maven-publish")
    id("com.modrinth.minotaur") version "2.+" apply false
}

repositories {
    mavenCentral()
}

val gversion = project.findProperty("general_version") as String

fun configure(v: String, maxExv: String, snapshot: Int = -1) {
    var changelog: String = Files.readString(Path.of(uri(project.file("CHANGELOG.md").getAbsolutePath())))
    var mv = v
    if (snapshot > -1)
        mv += "-snapshot-$snapshot"
    project(":$v") {
        extensions.extraProperties.apply {
            set("changelog", changelog)
            set("minecraft_version", mv)
            set("max_exc_version", maxExv)
            set("mod_version", "$gversion+$v")
        }
    }
}

configure("1.20", "1.21")
configure("1.21", "1.21.9")
configure("1.21.9", "1.21.12")
configure("26.1", "26.2", 4)