package kr.mrjimin.cosmeticsadder.util

import org.bukkit.Bukkit

object PluginUtil {

    fun isEnabled(plugin: String): Boolean {
        return Bukkit.getPluginManager().isPluginEnabled(plugin)
    }

}