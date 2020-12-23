package cf.lucasmellof.eventos.types

import cf.lucasmellof.eventos.components.EventComponents
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 01/12/2020
 */
class LotteryEvent : EventComponents {
    override val name: String = "loteria"
    override val fancyName: String = "Loteria"
    override var started: Long = 0L

    var result = 0

    override fun getAction() {
        getResult()
    }

    override fun showAnswer() {
        Bukkit.broadcastMessage("§6O resultado é: $result")
    }

    override fun onPlayer(p: Player, args: String?): Boolean {
        val sum = args!!.toIntOrNull()
        if (result == -1 && sum == null) return false
        return if (sum != result) {
            false
        } else {
            p.sendMessage("§eVocê acertou, parabéns!")
            true
        }
    }

    fun getResult() {
        result = (0..100).random()
        Bukkit.broadcastMessage("§6Digite um número entre 0 e 100.")
    }
}