package cf.lucasmellof.eventos.components

import cf.lucasmellof.eventos.LEventos
import cf.lucasmellof.eventos.config.ConfigManager
import cf.lucasmellof.eventos.events.PlayerWinEvent
import cf.lucasmellof.eventos.utils.Utils
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

    fun onFinish(p: Player, forced: Boolean)

    fun onPlayer(p: Player, args: String?): Boolean

    fun finalize(p: Player) {
        givePrize(p)
        LEventos.INSTANCE.runningEvent = null
    }

    fun showTime() {
        val current = System.currentTimeMillis() - started

        Bukkit.broadcastMessage("§6Demorou: §b${current / 1000.0}§fs")
    }

    fun givePrize(player: Player) {
        if (ConfigManager.prizeEnabled) {
            val text = ConfigManager.prizeCommand.replace("%player%", player.name)
            val replacedText = if (text == "") null else text
            Bukkit.getPluginManager().callEvent(
                PlayerWinEvent(
                    player,
                    ConfigManager.prize,
                    replacedText
                )
            )
            if (replacedText != null) Utils.executeConsoleCommand(
                Bukkit.getConsoleSender(),
                replacedText,
                LEventos.INSTANCE
            )
            Vault.eco?.depositPlayer(player, ConfigManager.prize)
        }
    }
}