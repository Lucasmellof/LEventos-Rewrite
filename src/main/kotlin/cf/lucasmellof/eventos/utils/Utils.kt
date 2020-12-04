package cf.lucasmellof.eventos.utils

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable


/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 03/12/2020
 */
object Utils {
    fun executeConsoleCommand(sender: CommandSender, commands: String, plugin: Plugin) {
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.dispatchCommand(sender, commands)
            }
        }.runTask(plugin)
    }
}