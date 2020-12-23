package cf.lucasmellof.eventos.types

import cf.lucasmellof.eventos.components.EventComponents
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class FastClickEvent : EventComponents {
    var token = ""

    override val name: String = "fastclick"
    override val fancyName: String = "Clique mais rápido"
    override var started: Long = 0L

    override fun getAction() {
        token = loadToken()
        Bukkit.getOnlinePlayers().forEach { it.spigot().sendMessage(click()) }
        Bukkit.broadcastMessage("§6Clique no botão para vencer.")
    }

    override fun showAnswer() {

    }

    override fun onPlayer(p: Player, args: String?): Boolean {
        return token == args
    }

    private fun click(): TextComponent {
        val cmp = TextComponent("CLIQUE AQUI RÁPIDO!")
        cmp.color = ChatColor.YELLOW
        cmp.isUnderlined = true
        cmp.isBold = true
        cmp.hoverEvent = HoverEvent(
            HoverEvent.Action.SHOW_TEXT, arrayOf<BaseComponent>(
                TextComponent("§aClique aqui para ganhar o evento clique rápido.")
            )
        )
        cmp.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, token)
        return cmp
    }

    private fun loadToken(): String {
        return (1..7).map { ('a'..'z').toList().toTypedArray().random() }.joinToString("")
    }
}