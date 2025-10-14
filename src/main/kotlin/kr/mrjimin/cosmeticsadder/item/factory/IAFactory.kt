package kr.mrjimin.cosmeticsadder.item.factory

import kr.mrjimin.cosmeticsadder.item.ItemHandler
import dev.lone.itemsadder.api.CustomStack
import org.bukkit.inventory.ItemStack

object IAFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return CustomStack.getInstance(id)!!.itemStack
    }
}