package kr.mrjimin.cosmeticsadder.cosmetics.impl

import com.hibiscusmc.hmccosmetics.api.HMCCosmeticsAPI
import kr.mrjimin.cosmeticsadder.cosmetics.Cosmetics
import org.bukkit.entity.Player

object HMCCosmeticsService : Cosmetics.Data {

    override val provider = "HMCCosmetics"

    override fun isCosmetics(key: String): Boolean = getCosmeticsByKey(key) != null

    override fun getCosmetics() =
        HMCCosmeticsAPI.getAllCosmetics().map { Cosmetics(provider, it.id, it.permission, it.item!!) }

    override fun getCosmeticsByKey(key: String) =
        HMCCosmeticsAPI.getCosmetic(key)?.let { Cosmetics(provider, it.id, it.permission, it.item!!) }

    override fun isInWardrobe(player: Player) =
        HMCCosmeticsAPI.getUser(player.uniqueId)?.isInWardrobe ?: false

    override fun equipCosmetics(key: String, player: Player) {
        val user = HMCCosmeticsAPI.getUser(player.uniqueId) ?: return
        HMCCosmeticsAPI.getCosmetic(key)?.let { HMCCosmeticsAPI.equipCosmetic(user, it) }
    }

    override fun unequipCosmetics(key: String, player: Player) {
        val user = HMCCosmeticsAPI.getUser(player.uniqueId) ?: return
        HMCCosmeticsAPI.getAllCosmeticSlots().values.forEach { slot ->
            user.getCosmetic(slot)?.takeIf { it.id == key }?.let { HMCCosmeticsAPI.unequipCosmetic(user, slot) }
        }
    }

    override fun getEquippedCosmetics(player: Player) =
        HMCCosmeticsAPI.getUser(player.uniqueId)?.let { user ->
            HMCCosmeticsAPI.getAllCosmeticSlots().values.mapNotNull { slot ->
                user.getCosmetic(slot)?.id?.let { HMCCosmeticsAPI.getCosmetic(it) }
                    ?.let { Cosmetics(provider, it.id, it.permission, it.item!!) }
            }
        } ?: emptyList()

    override fun hasPermission(player: Player, key: String) =
        HMCCosmeticsAPI.getCosmetic(key)?.let { player.hasPermission(it.permission) } ?: false

    override fun playerHasCosmetic(player: Player, key: String) =
        HMCCosmeticsAPI.getUser(player.uniqueId)?.let { user ->
            HMCCosmeticsAPI.getAllCosmeticSlots().values.any { slot -> user.getCosmetic(slot)?.id == key }
        } ?: false
}
