package cf.lucasmellof.eventos.components

import cf.lucasmellof.eventos.LEventos
import cf.lucasmellof.eventos.config.ConfigManager
import cf.lucasmellof.eventos.events.PlayerWinEvent
import cf.lucasmellof.eventos.utils.Vault
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
interface EventComponents : Listener {
    val name: String
    var started: Long
    fun onStart()

    fun onFinish(p: Player)

    fun onPlayer(p: Player, args: String?): Boolean

    fun finalize(p: Player) {
        Bukkit.getPluginManager().callEvent(
            PlayerWinEvent(
                p,
                ConfigManager.prize
            )
        )
        Vault.eco?.depositPlayer(p, ConfigManager.prize)
        LEventos.INSTANCE.runningEvent = null
    }

    fun showTime() {
        val current = System.currentTimeMillis() - started

        Bukkit.broadcastMessage("§6Demorou: §b${current / 1000.0}§fs")
    }
}