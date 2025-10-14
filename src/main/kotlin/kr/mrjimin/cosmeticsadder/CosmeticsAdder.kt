package kr.mrjimin.cosmeticsadder

import kr.mrjimin.cosmeticsadder.manager.CosmeticsManager
import kr.mrjimin.cosmeticsadder.manager.TagItemManager
import org.bukkit.plugin.java.JavaPlugin

class CosmeticsAdder : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: CosmeticsAdder
            private set
    }

    private val managers by lazy {
        listOf(
            CosmeticsManager,
            TagItemManager(this)
        )
    }

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        managers.forEach { it.setup() }
    }

    override fun onDisable() {
        managers.forEach { it.disable() }
    }
}