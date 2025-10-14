package kr.mrjimin.cosmeticsadder.item

import kr.mrjimin.cosmeticsadder.CosmeticsAdder
import kr.mrjimin.cosmeticsadder.item.factory.*
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

object ItemHandler {

    val KEY by lazy {
        NamespacedKey(CosmeticsAdder.INSTANCE, "id")
    }

    val TAG by lazy {
        NamespacedKey(CosmeticsAdder.INSTANCE, "tag")
    }

    val ITEM_FACTORIES = hashMapOf(
        "CRAFTENGINE" to CraftEngineFactory,
        "NEXO" to NexoFactory,
        "ITEMSADDER" to IAFactory
    )

    fun itemStackFromKey(namespace: String): ItemStack? {
        return if (namespace.contains(":")) {
            val id = namespace.split(":").first().uppercase()
            val factory = ITEM_FACTORIES[id] ?: return null
            factory.create(namespace.substring(id.length + 1))
        } else {
            null
        }
    }

    interface Factory {
        fun create(id: String): ItemStack?
    }
}