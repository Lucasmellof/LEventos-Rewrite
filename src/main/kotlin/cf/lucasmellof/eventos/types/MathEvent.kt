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

    override val needReply: Boolean = true
    override var started: Long = 0L

    override fun onStart() {
        val one = random.nextInt(20391)
        val two = random.nextInt(20391)
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fmatemática§6 iniciado.")
        Bukkit.broadcastMessage("§6Digite a resposta para vencer.")
        getResult(one, two)
        Bukkit.broadcastMessage(" ")
    }

    override fun onFinish(p: Player) {
        result = -1

        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fmatemática§6 concluido.")
        Bukkit.broadcastMessage("§6O grande vencedor foi: §f" + p.name)
        showTime()
        Bukkit.broadcastMessage(" ")
        finalize(p)
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

    fun getResult(one: Int, two: Int) {
        when ((1..4).random()) {
            1 -> {
                result = one + two
                Bukkit.broadcastMessage("§6Conta: §f$one §6+ §f$two")
            }
            2 -> {
                result = one - two
                Bukkit.broadcastMessage("§6Conta: §f$one §6- §f$two")
            }
            3 -> {
                result = one / two
                Bukkit.broadcastMessage("§6Conta: §f$one §6/ §f$two")
            }
            4 -> {
                result = one * two
                Bukkit.broadcastMessage("§6Conta: §f$one §6* §f$two")
            }
        }
    }
}