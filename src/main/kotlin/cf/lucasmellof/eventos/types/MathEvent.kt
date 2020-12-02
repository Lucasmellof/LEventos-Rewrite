package cf.lucasmellof.eventos.types

import cf.lucasmellof.eventos.components.EventComponents
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class MathEvent : EventComponents {
    private var result = 0
    private val random = Random()
    override val name: String = "math"

    override var started: Long = 0L

    override fun onStart() {
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fmatemática§6 iniciado.")
        Bukkit.broadcastMessage("§6Digite a resposta para vencer.")
        getResult()
        Bukkit.broadcastMessage(" ")
    }

    override fun onFinish(p: Player) {
        result = -1
        finalize(p)
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fmatemática§6 concluido.")
        Bukkit.broadcastMessage("§6O resultado é: $result")
        Bukkit.broadcastMessage("§6O grande vencedor foi: §f" + p.name)
        showTime()
        Bukkit.broadcastMessage(" ")

    }

    override fun onPlayer(p: Player, args: String?): Boolean {
        val sum = args!!.toIntOrNull()
        if (result == -1 && sum == null) return false
        return if (sum != result) {
            p.sendMessage("§cResultado errado, tente novamente.")
            false
        } else {
            p.sendMessage("§eVocê acertou, parabéns!")
            true
        }
    }

    fun getResult() {
        val numbers = (0..40391)
        val one = numbers.random()
        val two = numbers.random()

        when ((1..4).random()) {
            1 -> {
                result = one + two
                Bukkit.broadcastMessage("§6Conta: §f$one §6+ §f$two")
            }
            2 -> {
                result = one - two
                Bukkit.broadcastMessage("§6Conta: §f$one §6- §f$two")
            }
            4 -> {
                result = one * two
                Bukkit.broadcastMessage("§6Conta: §f$one §6* §f$two")
            }
        }
    }
}