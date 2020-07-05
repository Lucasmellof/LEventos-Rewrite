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
    override fun onStart() {
        val one = random.nextInt(20391)
        val two = random.nextInt(20391)
        result = one + two

        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fmatemática§6 iniciado.")
        Bukkit.broadcastMessage("§6Utilize §f/ev <resposta da conta>§6 para vencer.")
        Bukkit.broadcastMessage("§6Conta: §f$one §6+ §f$two")
        Bukkit.broadcastMessage(" ")
    }

    override fun onFinish(p: Player) {
        result = -1

        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fmatemática§6 concluido.")
        Bukkit.broadcastMessage("§6O grande vencedor foi: §f" + p.name)
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
}