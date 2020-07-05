package cf.lucasmellof.eventos.events

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class PlayerWinEvent(who: Player, val prize: Double) : PlayerEvent(who) {

    private val handlerList = HandlerList()

    override fun getHandlers(): HandlerList {
        return handlerList
    }
}