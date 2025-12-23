plugins {
    kotlin("jvm") version "2.3.0"
    id("fabric-loom") version "1.14-SNAPSHOT" apply false
    id("maven-publish")
}

repositories {
    mavenCentral()
}

fun configure(v: String, maxExv: String) {
    project(":$v") {
        extensions.extraProperties.apply {
            set("minecraft_version", v)
            set("max_exc_version", maxExv)
            set("mod_version", "${project.findProperty("general_version")}+$v")
        }
    }
}

configure("1.21", "1.21.9")
configure("1.21.9", "1.21.12")