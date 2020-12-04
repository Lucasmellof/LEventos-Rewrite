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
        getEverything(false)
        config.save()
    }

    fun getEverything(reload: Boolean) {
        if (reload) config.reload()
        prize = config.getDouble("prize.money", 0.0)
        prizeCommand = config.getString("prize.command", "")
        prizeEnabled = config.getBoolean("prize.enabled", false)

        disabledEvents = config.getStringList("disabledEvents", listOf())

        autoStartTime = config.getLong("autostart.time", 12000L)
        autoStartEnabled = config.getBoolean("autostart.enabled", false)
    }
}