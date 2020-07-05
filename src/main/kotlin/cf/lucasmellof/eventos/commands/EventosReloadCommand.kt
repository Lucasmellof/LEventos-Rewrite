package cf.lucasmellof.eventos.commands

import cf.lucasmellof.eventos.config.ConfigManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class EventosReloadCommand : CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if (p0.hasPermission("leventos.eventosreload")) {
            p0.sendMessage("§a§l ! §fRecarregando config.")
            ConfigManager.reload()
        } else return true
        return false
    }
}