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
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))
    compileOnly(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    testImplementation(group = "junit", name = "junit", version = "4.12")
}
bukkit {
    main = "cf.lucasmellof.eventos.LEventos"
    author = "Lucasmellof"
    name = "LEventos"
    version = "2.0"
    softDepend = listOf("Vault")
    commands.create("ev") {
        description = "Comando de eventos!"
        aliases = listOf("chatevento", "cevento", "eventos")
        usage = "Use o comando!"
    }
    commands.create("eventosreload") {
        description = "Comando de eventos!"
        permission = "leventos.eventosreload"
        usage = "Use o comando!"
    }
}