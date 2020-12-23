package cf.lucasmellof.eventos.utils

import org.apache.commons.lang3.time.DurationFormatUtils
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

    fun getFancyTime(value: Long): String {
        var result: String = DurationFormatUtils.formatDuration(value, "d").toString() + "d"
        if ("0d" == result) {
            result = DurationFormatUtils.formatDuration(value, "H").toString() + "h"
            if ("0h" == result) {
                result = DurationFormatUtils.formatDuration(value, "m").toString() + "m"
                if ("0m" == result) {
                    result = DurationFormatUtils.formatDuration(value, "s").toString() + "s"
                    if ("0s" == result) {
                        result = value.toString() + "ms"
                    }
                }
            }
        }
        return result
    }
}