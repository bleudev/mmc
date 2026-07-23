pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }
}
include(":1.21")
include(":1.21.9")
include(":26.1")
include(":26.2")
include(":26.3")