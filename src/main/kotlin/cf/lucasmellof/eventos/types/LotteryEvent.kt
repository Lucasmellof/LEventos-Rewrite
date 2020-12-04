package cf.lucasmellof.eventos.types

import cf.lucasmellof.eventos.components.EventComponents
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 01/12/2020
 */
class LotteryEvent : EventComponents {
    override val name: String = "loteria"
    override var started: Long = 0L

    var result = 0

    override fun onStart() {
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fLoteria§6 foi iniciado.")
        getResult()
        Bukkit.broadcastMessage(" ")
    }

    override fun onFinish(p: Player, forced: Boolean) {
        Bukkit.broadcastMessage(" ")
        Bukkit.broadcastMessage("§6Evento §fLoteria§6 foi concluido.")
        Bukkit.broadcastMessage("§6O resultado é: $result")
        if (!forced) {
            finalize(p)
            Bukkit.broadcastMessage("§6O grande vencedor foi: §f" + p.name)
            showTime()
        }
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
        result = (0..500).random()
        Bukkit.broadcastMessage("§6Digite um número entre 0 e 500.")
    }
}