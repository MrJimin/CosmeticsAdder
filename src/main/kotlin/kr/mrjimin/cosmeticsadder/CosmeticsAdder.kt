package kr.mrjimin.cosmeticsadder

import kr.mrjimin.cosmeticsadder.manager.CosmeticsManager
import org.bukkit.plugin.java.JavaPlugin

class CosmeticsAdder : JavaPlugin() {

    companion object {
        lateinit var INSTANCE: CosmeticsAdder
            private set
    }

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        CosmeticsManager.setup()
    }

}