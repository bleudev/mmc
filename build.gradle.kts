@file:Suppress("unused")

plugins {
    kotlin("jvm") version "2.3.20"
    kotlin("plugin.serialization") version "2.3.20"
    id("net.fabricmc.fabric-loom") version "1.15.5" apply false
    id("com.modrinth.minotaur") version "2.9.0" apply false
    id("maven-publish")
}

repositories {
    mavenCentral()
}

val gversion = project.findProperty("general_version") as String

private val changelog = project.file("CHANGELOG.md").readText()

private data class McInformation(val base: String, val dependency: String, val fabricModJson: String) {
    companion object {
        fun snapshot(v: String, num: Int): McInformation = McInformation(v, "$v-snapshot-$num", "$v-alpha.$num")
        fun pre(v: String, num: Int): McInformation = McInformation(v, "$v-pre-$num", "$v-pre.$num")
        fun rc(v: String, num: Int): McInformation = McInformation(v, "$v-rc-$num", "$v-rc.$num")
        fun release(v: String): McInformation = McInformation(v, v, v)
    }
}
private data class Deps(val dModMenu: String? = null, val dFabric: String? = null) {
    fun modmenu(new: String): Deps = Deps(new, dFabric)
    fun fabric(new: String): Deps = Deps(dModMenu, new)
}
private fun d() = Deps()

private fun prConfigure(projectAndMinecraftVersions: McInformation, maxExclusiveVersion: String, deps: Deps) {
    val versionSuffix = if (project.findProperty("beta_mode") == "true") "_beta" else ""
    project(":${projectAndMinecraftVersions.base}") {
        extensions.extraProperties.apply {
            set("mc_version", projectAndMinecraftVersions.dependency)
            set("min_mc_version", projectAndMinecraftVersions.fabricModJson)
            set("max_exc_version", maxExclusiveVersion)
            set("mod_version", "$gversion$versionSuffix+${projectAndMinecraftVersions.base}")
            set("changelog", changelog)

            if (deps.dFabric != null) set("fabric_version", deps.dFabric)
            if (deps.dModMenu != null) set("modmenu_version", deps.dModMenu)
        }
    }
}
private fun prConfigure(v: String, maxExv: String, deps: Deps) = prConfigure(McInformation.release(v), maxExv, deps)

private fun String.snapshot(num: Int): McInformation = McInformation.snapshot(this, num)
private fun String.pre(num: Int): McInformation = McInformation.pre(this, num)
private fun String.rc(num: Int): McInformation = McInformation.rc(this, num)

prConfigure("1.21", "1.21.9", d().fabric("0.102.0+1.21").modmenu("11.0.3"))
prConfigure("1.21.9", "1.21.12", d().fabric("0.134.0+1.21.9").modmenu("16.0.0-rc.1"))
prConfigure("26.1", "26.2", d().fabric("0.144.3+26.1").modmenu("18.0.0-alpha.8"))
prConfigure("26.2".pre(2), "26.2", d().fabric("0.150.1+26.2").modmenu("20.0.0-alpha.1"))