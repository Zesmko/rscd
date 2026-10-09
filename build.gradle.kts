plugins {
    id("java")
    id("net.fabricmc.fabric-loom")
    id("maven-publish")
}

group = "dev.noeul.fabricmod"
version = property("mod_version").toString()

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    implementation("net.fabricmc:fabric-loader:${property("loader_version")}")
}

val targetJavaVersion = JavaVersion.toVersion(property("target_java_version").toString())

java {
    sourceCompatibility = targetJavaVersion
    targetCompatibility = targetJavaVersion
}

tasks {
    compileJava {
        options.release.set(targetJavaVersion.majorVersion.toInt())
        options.encoding = "UTF-8"
    }

    processResources {
        val inputs = mapOf(
            "mod_id" to project.property("mod_id"),
            "version" to project.version,
            "name" to project.property("mod_name"),
            "minecraft_version" to project.property("minecraft_version"),
            "loader_version" to project.property("loader_version")
        )

        this.inputs.properties(inputs)

        filesMatching("fabric.mod.json") {
            expand(inputs)
        }
    }

    jar {
        from("LICENSE.txt")
        archiveBaseName.set("${project.property("archive_base_name")}")
        archiveAppendix.set("fabric")
        archiveVersion.set("${project.version}+mc${project.property("minecraft_version")}")
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}