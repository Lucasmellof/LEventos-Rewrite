package cf.lucasmellof.eventos.commands

import cf.lucasmellof.eventos.LEventos
import cf.lucasmellof.eventos.config.ConfigManager
import cf.lucasmellof.eventos.utils.Text
import me.saiintbrisson.minecraft.command.annotation.Command
import me.saiintbrisson.minecraft.command.command.Context
import me.saiintbrisson.minecraft.command.target.CommandTarget
import org.bukkit.entity.Player


/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 01/12/2020
 */
class EventoCommand {

    @Command(
        name = "eventos",
        aliases = ["evento", "ev", "leventos", "event"],
        target = CommandTarget.PLAYER,
        permission = "leventos.admin"
    )
    fun mainCommand(ctx: Context<Player>) {
        val p = ctx.sender
        return showHelp(p, ctx.label)
    }

    @Command(
        name = "eventos.start",
        aliases = ["iniciar"],
        target = CommandTarget.PLAYER,
        permission = "leventos.admin"
    )
    fun startCommand(ctx: Context<Player>, eventName: String) {
        val p = ctx.sender
        val event = LEventos.INSTANCE.loadedEvents[eventName.toLowerCase()]
        if (event == null) {
            p.sendMessage("§c ! §fNão encontrei nenhum evento com esse nome.")
            Text.sendTo(
                p,
                Text(
                    "§f§l[CLIQUE AQUI PARA VER OS EVENTOS DISPONÍVEIS]",
                    "Mostra os comandos disponíveis"
                ).setRunCommandActionText("/${ctx.label} list")
            )
            return
        }
        LEventos.INSTANCE.runningEvent = event
        event.onStart()
        p.sendMessage("§a ! §fVocê iniciou um evento")
    }

    @Command(name = "eventos.reload", permission = "leventos.admin")
    fun reloadCommand(ctx: Context<Player>) {
        ctx.sender.sendMessage("§a§l ! §fRecarregando config.")
        ConfigManager.reload()
    }

    @Command(name = "eventos.list", target = CommandTarget.PLAYER, permission = "leventos.admin")
    fun listCommand(ctx: Context<Player>) {
        val p = ctx.sender
        p.sendMessage("§7(§m§l----§3§lLista de Eventos Disponíveis§7§m§l----§7)")
        for (loadedEvent in LEventos.INSTANCE.loadedEvents) {
            Text.sendTo(
                p,
                Text(
                    "§f[§c${loadedEvent.value.name.capitalize()}§f]",
                    "Clique aqui para iniciar o evento"
                ).setRunCommandActionText("/eventos start ${loadedEvent.value.name}")
            )
        }
    }

    private fun showHelp(p: Player, label: String) {
        if (p.hasPermission("leventos.admin")) {
            p.sendMessage("")
            Text.sendTo(p, Text("§3§l/§a$label", "mostra esse comando", "/$label <evento>"))
            Text.sendTo(p, Text("§3§l/§a$label §estart §d<nome do evento>", "Inicia um evento", "/$label start "))
            Text.sendTo(p, Text("§3§l/§a$label §elist", "Mostra os eventos disponíveis", "/$label list"))
            Text.sendTo(
                p,
                Text("§3§l/§a$label §ereload", "Recarrega todas as configurações do plugin", "/$label reload")
            )
        } else {
            p.sendMessage("§a ! §fPlugin criado por Lucasmellof")
            p.sendMessage("§a ! §fhttps://github.com/Lucasmellof")
            p.sendMessage("§a ! §fDiscord: carmello#0760")
        }
    }
}