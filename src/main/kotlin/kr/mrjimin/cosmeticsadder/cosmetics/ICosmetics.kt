package kr.mrjimin.cosmeticsadder.cosmetics

import org.bukkit.entity.Player

interface ICosmetics {

    val provider: String

    fun isCosmetics(key: String): Boolean
    fun getCosmetics(): List<CosmeticsData>
    fun getCosmeticsByKey(key: String): CosmeticsData?

    fun isInWardrobe(player: Player): Boolean

    fun equipCosmetics(key: String, player: Player)
    fun unequipCosmetics(key: String, player: Player)
    fun getEquippedCosmetics(player: Player): List<CosmeticsData>

    fun hasPermission(player: Player, key: String): Boolean

    fun playerHasCosmetic(player: Player, key: String): Boolean
}