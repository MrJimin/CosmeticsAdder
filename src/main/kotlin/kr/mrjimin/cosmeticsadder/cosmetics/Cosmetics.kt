package kr.mrjimin.cosmeticsadder.cosmetics

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

data class Cosmetics(
    val plugin: String,
    val key: String,
    val permission: String,
    val itemStack: ItemStack
) {
    interface Data {

        val provider: String

        fun isCosmetics(key: String): Boolean
        fun getCosmetics(): List<Cosmetics>
        fun getCosmeticsByKey(key: String): Cosmetics?

        fun isInWardrobe(player: Player): Boolean

        fun equipCosmetics(key: String, player: Player)
        fun unequipCosmetics(key: String, player: Player)
        fun getEquippedCosmetics(player: Player): List<Cosmetics>

        fun hasPermission(player: Player, key: String): Boolean

        fun playerHasCosmetic(player: Player, key: String): Boolean
    }
}
