plugins {
    kotlin("jvm") version "2.2.21"
    id("fabric-loom") version "1.13.6" apply false
    id("maven-publish")
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

configure("1.21", "1.21.11")