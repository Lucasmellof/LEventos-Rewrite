package cf.lucasmellof.eventos.commands

import cf.lucasmellof.eventos.LEventos
import cf.lucasmellof.eventos.components.EventComponents
import cf.lucasmellof.eventos.config.ConfigManager
import cf.lucasmellof.eventos.events.PlayerWinEvent
import cf.lucasmellof.eventos.types.FastClickEvent
import cf.lucasmellof.eventos.types.HoverEvent
import cf.lucasmellof.eventos.types.MathEvent
import cf.lucasmellof.eventos.utils.Vault
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class EventoCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String?, args: Array<String>): Boolean {
        var event: EventComponents? = LEventos.INSTANCE.runningEvent
        if (sender is Player) {
            val p: Player = sender
            if (args.isNullOrEmpty()) {
                when (event) {
                    is MathEvent -> {
                        p.sendMessage("§e ! §fUse §e/ev <resposta>.")
                    }
                    is FastClickEvent -> {
                        p.sendMessage("§c ! §fSem comandos neste evento.")
                    }
                    is HoverEvent -> {
                        p.sendMessage("§e ! §fUse §e/ev <resposta>.")
                    }
                    else -> {
                        p.sendMessage("§cNenhum evento está ocorrendo no momento.")
                    }
                }
            }
            if (event == null) {
                p.sendMessage("§cNenhum evento está ocorrendo no momento.")
                return true
            }
            if (args[0].equals("matematica", ignoreCase = true) && p.hasPermission("leventos.eventos")) {
                if (event != null) {
                    p.sendMessage("§cJá está ocorrendo um evento.")
                    return true
                }
                event = MathEvent()
                event.onStart()
                p.sendMessage("§aVocê iniciou o evento matematica.")
            } else if (args[0].equals("fastclick", ignoreCase = true) && p.hasPermission("leventos.eventos")) {
                if (event != null) {
                    p.sendMessage("§cJá está ocorrendo um evento.")
                    return true
                }
                event = FastClickEvent()
                event.onStart()
                p.sendMessage("§aVocê iniciou o evento clique rápido.")
            } else if (args[0].equals("hover", ignoreCase = true) && p.hasPermission("leventos.eventos")) {
                if (event != null) {
                    p.sendMessage("§cJá está ocorrendo um evento.")
                    return true
                }
                event = HoverEvent()
                event.onStart()
                p.sendMessage("§aVocê iniciou o evento escreva mais rapido.")
            } else {
                if (event.onPlayer(p, args[0])) {
                    event.onFinish(p)
                    Bukkit.getPluginManager().callEvent(
                        PlayerWinEvent(
                            p,
                            ConfigManager.prize
                        )
                    )
                    Vault.eco?.depositPlayer(p, ConfigManager.prize)
                    LEventos.INSTANCE.runningEvent = null
                }
            }
        }
        return true
    }
}