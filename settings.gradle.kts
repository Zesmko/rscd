pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
    }

    val loomVersion = providers.gradleProperty("loom_version").get()

    plugins {
        id("net.fabricmc.fabric-loom") version loomVersion
    }
}

rootProject.name = "rscd"