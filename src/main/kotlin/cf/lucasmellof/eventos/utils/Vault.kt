package cf.lucasmellof.eventos.utils

import cf.lucasmellof.eventos.LEventos
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.RegisteredServiceProvider

object Vault {
    var eco: Economy? = null

    fun setupEconomy(): Boolean {
        if (LEventos.INSTANCE.server.pluginManager.getPlugin("Vault") == null) {
            Bukkit.getConsoleSender().sendMessage("Vault não está presente no servidor, plugin finalizando")
            Bukkit.shutdown()
            return false
        }
        val rsp: RegisteredServiceProvider<Economy> =
            LEventos.INSTANCE.server.servicesManager.getRegistration(Economy::class.java) ?: return false
        eco = rsp.provider
        return eco != null
    }
}
