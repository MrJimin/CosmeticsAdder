package kr.mrjimin.cosmeticsadder.item.factory

import kr.mrjimin.cosmeticsadder.item.ItemHandler
import com.nexomc.nexo.api.NexoItems
import org.bukkit.inventory.ItemStack

object NexoFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return NexoItems.itemFromId(id)?.build()
    }
}