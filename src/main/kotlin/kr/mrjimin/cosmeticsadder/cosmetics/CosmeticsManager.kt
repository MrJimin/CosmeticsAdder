package kr.mrjimin.cosmeticsadder.cosmetics

import kr.mrjimin.cosmeticsadder.cosmetics.impl.CosmeticsCoreService
import kr.mrjimin.cosmeticsadder.cosmetics.impl.HMCCosmeticsService
import kr.mrjimin.cosmeticsadder.util.PluginUtil
import kr.mrjimin.cosmeticsadder.util.event
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent

object CosmeticsManager {

    lateinit var cosmeticsData: Cosmetics.Data
        private set

    fun setup() {
        cosmeticsData = detectAvailablePlugin()
            ?: throw IllegalStateException("No supported cosmetics plugin found.")

//        event<PlayerJoinEvent>(ignoreCancelled = true) {
//            cosmeticsData.getCosmetics().firstOrNull()?.let { cosmetic ->
//                it.player.sendMessage("pluginName: ${cosmetic.plugin}")
//                it.player.sendMessage("key: ${cosmetic.key}")
//                it.player.sendMessage("permission: ${cosmetic.permission}")
//                it.player.sendMessage("itemStack: ${cosmetic.itemStack}")
//                it.player.sendMessage("")
//                it.player.sendMessage("isCosmetic: ${cosmeticsData.isCosmetics("beanie")}")
//            } ?: it.player.sendMessage("No cosmetics found")
//        }
    }

    private fun detectAvailablePlugin(): Cosmetics.Data? {
        val plugins = listOf(
            "HMCCosmetics" to HMCCosmeticsService(),
            "CosmeticsCore" to CosmeticsCoreService()
        )

        return plugins.firstNotNullOfOrNull { (name, service) ->
            if (PluginUtil.isEnabled(name)) {
                Bukkit.getConsoleSender().sendMessage("§e[CosmeticsAdder] Hooked into '$name' successfully! Using ${service::class.simpleName}.")
                service
            } else null
        }
    }

}
