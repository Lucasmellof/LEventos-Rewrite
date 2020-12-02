package cf.lucasmellof.eventos

import cf.lucasmellof.eventos.commands.EventoCommand
import cf.lucasmellof.eventos.components.EventComponents
import cf.lucasmellof.eventos.config.ConfigManager
import cf.lucasmellof.eventos.listeners.PlayerListeners
import cf.lucasmellof.eventos.types.FastClickEvent
import cf.lucasmellof.eventos.types.HoverEvent
import cf.lucasmellof.eventos.types.LotteryEvent
import cf.lucasmellof.eventos.types.MathEvent
import cf.lucasmellof.eventos.utils.Vault
import me.saiintbrisson.bukkit.command.BukkitFrame
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class LEventos : JavaPlugin() {
    companion object {
        lateinit var INSTANCE: LEventos
    }

    var runningEvent: EventComponents? = null
    lateinit var frame: BukkitFrame

    val loadedEvents = hashMapOf<String, EventComponents>()

    override fun onEnable() {
        INSTANCE = this
        Bukkit.getConsoleSender().sendMessage("§a ! §fCarregando o plugin de eventos")
        Bukkit.getConsoleSender().sendMessage("§a ! §fPlugin criado por Lucasmellof")
        Bukkit.getConsoleSender().sendMessage("§a ! §fhttps://github.com/Lucasmellof")
        Bukkit.getConsoleSender().sendMessage("§a ! §fDiscord: carmello#0760")
        registerAndLoadCommands(this)
        registerEvent(FastClickEvent())
        registerEvent(MathEvent())
        registerEvent(HoverEvent())
        registerEvent(LotteryEvent())
        Bukkit.getPluginManager().registerEvents(PlayerListeners(), this)
        ConfigManager.initialize(this)
        Vault.setupEconomy()
        //eventScheduler()

    }

    private fun registerAndLoadCommands(plugin: JavaPlugin) {
        frame = BukkitFrame(plugin)
        frame.registerCommands(EventoCommand())
    }

    private fun registerEvent(event: EventComponents) {
        loadedEvents[event.name] = event
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
        }.runTaskTimer(this, 0, ConfigManager.autoStartTime * 20)
    }
}