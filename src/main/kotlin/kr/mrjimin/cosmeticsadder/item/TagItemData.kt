package kr.mrjimin.cosmeticsadder.item

import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey

data class TagItemData(
    val key: String,
    val enabled: Boolean,
    val material: String,
    val displayName: Component?,
    val lore: MutableList<Component>?,
    val itemModel: NamespacedKey?,
    val tag: Component
)