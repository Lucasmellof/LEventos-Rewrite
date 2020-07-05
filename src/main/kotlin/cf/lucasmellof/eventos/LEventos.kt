package cf.lucasmellof.eventos

import cf.lucasmellof.eventos.commands.EventoCommand
import cf.lucasmellof.eventos.commands.EventosReloadCommand
import cf.lucasmellof.eventos.components.EventComponents
import cf.lucasmellof.eventos.config.ConfigManager
import cf.lucasmellof.eventos.types.FastClickEvent
import cf.lucasmellof.eventos.types.HoverEvent
import cf.lucasmellof.eventos.types.MathEvent
import cf.lucasmellof.eventos.utils.Vault
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class LEventos : JavaPlugin() {
    companion object {
        val INSTANCE: LEventos = getPlugin(LEventos::class.java)
    }

    var runningEvent: EventComponents? = null
    override fun onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a ! §fCarregando o plugin de eventos")
        Bukkit.getConsoleSender().sendMessage("§a ! §fPlugin criado por Lucasmellof")
        Bukkit.getConsoleSender().sendMessage("§a ! §fhttps://github.com/Lucasmellof")
        Bukkit.getConsoleSender().sendMessage("§a ! §fDiscord: carmello#0760")
        ConfigManager.initialize()
        Vault.setupEconomy()
        getCommand("ev").executor = EventoCommand()
        getCommand("reloadevento").executor = EventosReloadCommand()
        eventScheduler()
    }

    private fun eventScheduler() {
        var event = 0
        object : BukkitRunnable() {
            override fun run() {
                when (event) {
                    0 -> {
                        if (runningEvent != null) runningEvent = null
                        runningEvent = MathEvent()
                        runningEvent!!.onStart()
                        event++
                    }
                    1 -> {
                        if (runningEvent != null) runningEvent = null
                        runningEvent = FastClickEvent()
                        runningEvent!!.onStart()
                        event++
                    }
                    2 -> {
                        if (runningEvent != null) runningEvent = null
                        runningEvent = HoverEvent()
                        runningEvent!!.onStart()
                        event = 0
                    }
                }
            }
        }.runTaskTimer(this, 0, ConfigManager.autoStartTime)
    }
}