package cf.lucasmellof.eventos.config

import cf.lucasmellof.eventos.LEventos
import cf.lucasmellof.huddout.utils.ConfigAPI

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
object ConfigManager {
    lateinit var config: ConfigAPI

    var prize: Double = 0.0
    var autoStart: Boolean = false
    var autoStartTime: Long = 0

    fun initialize() {
        config = ConfigAPI(LEventos.INSTANCE, "config.yml", false)

        prize = config.getOrSetDefaultValue("prize", 20.0)
        autoStart = config.getOrSetDefaultValue("autostart.enabled", false)
        autoStartTime = config.getOrSetDefaultValue("autostart.time", 10L)

        config.save()
    }
}