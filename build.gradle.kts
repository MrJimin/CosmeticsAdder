import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.2.10"
    java
    id("com.gradleup.shadow") version "9.0.0-beta11"
}

group = "kr.mrjimin.cosmeticsadder"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")

    }

    maven {
        url = uri("https://repo.hibiscusmc.com/releases")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")

    compileOnly(fileTree("lib") {
        include("*.jar")
    })

    compileOnly("com.hibiscusmc:HMCCosmetics:2.8.2-fed13b8e")
}

kotlin {
    jvmToolchain(21)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.register<ShadowJar>("shadowJarPlugin") {
    archiveFileName.set("CosmeticsAdder-${project.version}.jar")

    from(sourceSets.main.get().output)
    configurations = listOf(project.configurations.runtimeClasspath.get())

    exclude("com/zaxxer/**")
    exclude("kotlin/**", "kotlinx/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
    exclude("org/slf4j/**")
}

tasks {
    build {
        dependsOn("shadowJarPlugin")
    }

    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}