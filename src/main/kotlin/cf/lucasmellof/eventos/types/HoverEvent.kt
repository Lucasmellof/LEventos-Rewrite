package cf.lucasmellof.eventos.types

import cf.lucasmellof.eventos.components.EventComponents
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class HoverEvent : EventComponents {
    var token = ""

    override val name: String = "hover"

    override var started: Long = 0L

    override fun onStart() {
        token = loadToken()
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fescreva mais rapido§6 foi iniciado.")
        Bukkit.broadcastMessage("§6Digite a resposta para vencer.")
        Bukkit.getOnlinePlayers().forEach { it.spigot().sendMessage(message()) }
        Bukkit.broadcastMessage(" ")
        started = System.currentTimeMillis()
    }

    override fun onFinish(p: Player) {
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fescreva mais rapido§6 ocorrido.")
        Bukkit.broadcastMessage("§6O grande vencedor foi: §f" + p.name)
        showTime()
        Bukkit.broadcastMessage(" ")
        finalize(p)
    }

    override fun onPlayer(p: Player, args: String?): Boolean {
        return token == args
    }

    private fun message(): TextComponent {
        val cmp = TextComponent("Passe o mouse para ver a mensagem.")
        cmp.color = ChatColor.YELLOW
        cmp.isUnderlined = true
        cmp.isBold = true
        cmp.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf<BaseComponent>(TextComponent(token)))
        return cmp
    }

    private fun loadToken(): String {
        return (1..7).map { ('a'..'Z').toList().toTypedArray().random() }.joinToString("")
    }
}