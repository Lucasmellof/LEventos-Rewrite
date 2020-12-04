package cf.lucasmellof.eventos.listeners

import cf.lucasmellof.eventos.LEventos
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 01/12/2020
 */
class PlayerListeners : Listener {
    @EventHandler
    fun onMessage(event: AsyncPlayerChatEvent) {
        if (LEventos.INSTANCE.runningEvent == null) {
            return
        }
        val evento = LEventos.INSTANCE.runningEvent!!
        if (evento.onPlayer(event.player, event.message)) {
            evento.onFinish(event.player, false)
            event.isCancelled = true
        }
    }
}