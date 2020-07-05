package cf.lucasmellof.eventos.config

import cf.lucasmellof.eventos.utils.ConfigAPI
import org.bukkit.plugin.Plugin

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
object ConfigManager {
    lateinit var config: ConfigAPI

    var prize: Double = 0.0
    var autoStartTime: Long = 0

    fun initialize(instance: Plugin) {
        config = ConfigAPI(instance, "config.yml", true, header = true)

        prize = config.getDouble("prize")
        autoStartTime = config.getLong("autostart.time")

        config.save()
    }

    fun reload() {
        config.reload()
    }
}