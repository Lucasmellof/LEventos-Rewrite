package cf.lucasmellof.eventos.types

import cf.lucasmellof.eventos.components.EventComponents
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class MathEvent : EventComponents {
    private var result = 0
    override val name: String = "math"
    override val fancyName: String = "Matemática"

    override var started: Long = 0L

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

    private fun getResult() {
        val numbers = (0..40391)
        val one = numbers.random()
        val two = numbers.random()

        when ((1..3).random()) {
            1 -> {
                result = one + two
                Bukkit.broadcastMessage("§6Conta: §f$one §6+ §f$two")
            }
            2 -> {
                result = one - two
                Bukkit.broadcastMessage("§6Conta: §f$one §6- §f$two")
            }
            3 -> {
                result = one * two
                Bukkit.broadcastMessage("§6Conta: §f$one §6* §f$two")
            }
        }
    }
}