package kr.mrjimin.cosmeticsadder.listener

import kr.mrjimin.cosmeticsadder.util.event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener {

    init {
        event<PlayerJoinEvent>(ignoreCancelled = true) {
            it.player.sendMessage("aaa")
        }
    }
}