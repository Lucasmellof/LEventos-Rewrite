package cf.lucasmellof.eventos.components

import org.bukkit.entity.Player
import org.bukkit.event.Listener

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
interface EventComponents : Listener {
    fun onStart()

    fun onFinish(p: Player)

    fun onPlayer(p: Player, args: String?): Boolean
}