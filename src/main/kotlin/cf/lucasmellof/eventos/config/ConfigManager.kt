package cf.lucasmellof.eventos.config

import cf.lucasmellof.eventos.utils.ConfigAPI
import org.bukkit.plugin.Plugin

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
object ConfigManager {
    lateinit var config: ConfigAPI

    var prize: Double = 0.0
    var prizeCommand: String = ""
    var prizeEnabled = false

    var autoStartEnabled = false
    var autoStartTime: Long = 0

    var disabledEvents = listOf<String>()

    fun initialize(instance: Plugin) {
        config = ConfigAPI(instance, "config.yml", true, header = true)

        prize = config.getOrSetDefaultValue("prize.money", 0.0)
        prizeCommand = config.getOrSetDefaultValue("prize.command", "")
        prizeEnabled = config.getOrSetDefaultValue("prize.enabled", false)

        disabledEvents = config.getOrSetDefaultValue("disabledEvents", listOf())

        autoStartTime = config.getOrSetDefaultValue("autostart.time", 12000L)
        autoStartEnabled = config.getOrSetDefaultValue("autostart.enabled", false)

        config.save()
    }

    fun reload() {
        config.reload()
    }
}