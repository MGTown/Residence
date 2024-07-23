plugins {
    id ("java")
    id ("io.papermc.paperweight.userdev") version ("1.7.1")
    id ("xyz.jpenilla.run-paper") version ("2.3.0")
}

group = "cn.mgtown.residence"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.enginehub.org/repo/")
    maven("https://jitpack.io")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-core:7.3.4")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.4")
    compileOnly("com.github.Zrips:CMILib:1.4.7.4")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks {
    compileJava {
        options.release = 21
    }
}