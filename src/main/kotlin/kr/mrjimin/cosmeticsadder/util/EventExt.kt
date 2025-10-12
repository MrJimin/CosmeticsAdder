package kr.mrjimin.cosmeticsadder.util

import kr.mrjimin.cosmeticsadder.CosmeticsAdder
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import java.util.function.Consumer

fun Listener.register() {
    CosmeticsAdder.INSTANCE.server.pluginManager.registerEvents(this, CosmeticsAdder.INSTANCE)
}

inline fun <reified T : Event> event(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    callback: Consumer<T>
): Listener {
    val listener = object : Listener {}
    Bukkit.getPluginManager().registerEvent(
        T::class.java,
        listener,
        priority,
        { _, event ->
            if (event is T) {
                callback.accept(event)
            }
        },
        CosmeticsAdder.INSTANCE,
        ignoreCancelled
    )
    return listener
}