package kr.mrjimin.cosmeticsadder.cosmetics

import org.bukkit.inventory.ItemStack

data class CosmeticsData(
    val plugin: String,
    val key: String,
    val permission: String,
    val itemStack: ItemStack
)
