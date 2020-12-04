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
import org.bukkit.scheduler.BukkitTask

/* 
 * @author Lucasmellof, Lucas de Mello Freitas created on 05/07/2020
 */
class LEventos : JavaPlugin() {
    companion object {
        lateinit var INSTANCE: LEventos
    }

    var runningEvent: EventComponents? = null
    lateinit var frame: BukkitFrame

    private lateinit var task: BukkitTask
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
        eventScheduler()

    }

    private fun registerAndLoadCommands(plugin: JavaPlugin) {
        frame = BukkitFrame(plugin)
        frame.registerCommands(EventoCommand())
    }

    private fun registerEvent(event: EventComponents) {
        if (ConfigManager.disabledEvents.contains(event.name)) {
            Bukkit.getConsoleSender()
                .sendMessage("§e ! §fEvento '${event.name}' encontrado, mas desabilitado na config")
            return
        }
        loadedEvents[event.name] = event
        Bukkit.getConsoleSender().sendMessage("§e ! §fEvento '${event.name}' encontrado e carregado")
    }

    private fun eventScheduler() {
        var current = 0
        val max = loadedEvents.keys.toList()
        Bukkit.getConsoleSender()
            .sendMessage("§a ! §fIniciando os eventos automáticos, os eventos vão iniciar a cada ${ConfigManager.autoStartTime}s")
        task = object : BukkitRunnable() {
            override fun run() {
                if (server.onlinePlayers.isEmpty()) return
                runningEvent = loadedEvents[max[current]]
                runningEvent!!.onStart()
                current++
                if (current == max.size) current = 0
            }
        }.runTaskTimerAsynchronously(this, 0, ConfigManager.autoStartTime * 20)
    }

    fun reScheduleEvent() {
        task.cancel()
        eventScheduler()
    }
}