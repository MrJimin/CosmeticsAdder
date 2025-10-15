package kr.mrjimin.cosmeticsadder.manager

import kr.mrjimin.cosmeticsadder.cosmetics.Cosmetics
import kr.mrjimin.cosmeticsadder.cosmetics.impl.CosmeticsCoreService
import kr.mrjimin.cosmeticsadder.cosmetics.impl.HMCCosmeticsService
import org.bukkit.Bukkit

object CosmeticsManager {

    lateinit var cosmeticsData: Cosmetics.Data
        private set

    val PLUGINS = mapOf(
        "HMCCosmetics" to HMCCosmeticsService,
        "CosmeticsCore" to CosmeticsCoreService
    )

    fun setup() {
        cosmeticsData = PLUGINS.entries.firstNotNullOfOrNull { (name, service) ->
            if (isPluginEnabled(name)) {
                Bukkit.getConsoleSender().sendMessage("§e[CosmeticsAdder] Hooked into '$name' successfully! Using ${service::class.simpleName}.")
                service
            } else null
        } ?: throw IllegalStateException("No supported cosmetics plugin found.")
    }

    private fun isPluginEnabled(name: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(name)
    }

}