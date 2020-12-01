import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
}

group = "cf.lucasmellof"
version = "2.0"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))
    compileOnly(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("com.github.SaiintBrisson.command-framework:bukkit:1.0.0")
    testImplementation(group = "junit", name = "junit", version = "4.12")
}
bukkit {
    main = "cf.lucasmellof.eventos.LEventos"
    author = "Lucasmellof"
    name = "LEventos"
    version = "2.0"
    softDepend = listOf("Vault")
    website = "https://lucasmellof.cf"
}