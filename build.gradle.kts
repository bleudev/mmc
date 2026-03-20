import java.nio.file.Files
import java.nio.file.Path

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.serialization") version "2.3.0"
    id("net.fabricmc.fabric-loom") version "1.15.5" apply false
    id("maven-publish")
    id("com.modrinth.minotaur") version "2.+" apply false
}

repositories {
    mavenCentral()
}

val gversion = project.findProperty("general_version") as String

private val changelog = project.file("CHANGELOG.md").readText()

private fun prConfigure(projectAndMinecraftVersions: Pair<String, String>, maxExclusiveVersion: String) {
    val versionSuffix = if (project.findProperty("beta_mode") == "true") "_beta" else ""
    project(":${projectAndMinecraftVersions.first}") {
        extensions.extraProperties.apply {
            set("minecraft_version", projectAndMinecraftVersions.second)
            set("max_exc_version", maxExclusiveVersion)
            set("mod_version", "${project.findProperty("general_version")}$versionSuffix+${projectAndMinecraftVersions.first}")
            set("changelog", changelog)
        }
    }
}
private fun prConfigure(v: String, maxExv: String) = prConfigure(v to v, maxExv)

private fun String.snapshot(num: Int): Pair<String, String> = this to "$this-snapshot-$num"
private fun String.pre(num: Int): Pair<String, String> = this to "$this-pre-$num"
private fun String.rc(num: Int): Pair<String, String> = this to "$this-rc-$num"

prConfigure("1.20", "1.21")
prConfigure("1.21", "1.21.9")
prConfigure("1.21.9", "1.21.12")
prConfigure("26.1".pre(2), "26.2")