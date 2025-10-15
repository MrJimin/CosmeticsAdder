package kr.mrjimin.cosmeticsadder.cosmetics.impl

import dev.lone.cosmeticscore.api.temporary.CosmeticAccessor
import dev.lone.cosmeticscore.api.temporary.CosmeticsCoreApi
import kr.mrjimin.cosmeticsadder.cosmetics.Cosmetics
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object CosmeticsCoreService : Cosmetics.Data {

    override val provider = "CosmeticsCore"

    override fun isCosmetics(key: String): Boolean = getCosmeticsByKey(key) != null

    override fun getCosmetics(): List<Cosmetics> =
        getAnyonePlayer()?.let { player ->
            getAllKeys().mapNotNull { getCosmeticsByKey(it, player) }
        } ?: emptyList()

    override fun getCosmeticsByKey(key: String): Cosmetics? =
        getCosmeticsByKey(key, getAnyonePlayer())

    override fun isInWardrobe(player: Player): Boolean = CosmeticsCoreApi.isInWardrobe(player)

    override fun equipCosmetics(key: String, player: Player) {
        getCosmeticsAccessor(key, player).equip()
    }

    override fun unequipCosmetics(key: String, player: Player) {
        getCosmeticsAccessor(key, player).unequip()
    }

    override fun getEquippedCosmetics(player: Player): List<Cosmetics> =
        CosmeticsCoreApi.getEquippedCosmeticsAccessors(player)
            .mapNotNull { it as? CosmeticAccessor }
            .mapNotNull { getCosmeticsByCosmeticAccessor(it) }

    override fun hasPermission(player: Player, key: String): Boolean =
        getCosmeticsByKey(key, player)?.let { player.hasPermission(it.permission) } ?: false

    override fun playerHasCosmetic(player: Player, key: String): Boolean =
        getEquippedCosmetics(player).any { it.key == key }

    private fun getCosmeticsByCosmeticAccessor(accessor: CosmeticAccessor): Cosmetics? =
        runCatching {
            Cosmetics(
                provider,
                accessor.key,
                "cosmeticscore.user.cosmetics.wear.${accessor.key}",
                accessor.guiModelItem
            )
        }.getOrNull()

    private fun getCosmeticsByKey(key: String, player: Player?): Cosmetics? =
        player?.let { getCosmeticsAccessor(key, it).let(::getCosmeticsByCosmeticAccessor) }

    private fun getCosmeticsAccessor(key: String, player: Player): CosmeticAccessor =
        CosmeticsCoreApi.newCosmeticAccessor(key, player) as CosmeticAccessor

    private fun getAllKeys(): List<String> =
        @Suppress("UNCHECKED_CAST")
        (CosmeticsCoreApi.getCosmeticsKeysCopy() as List<String>)

    private fun getAnyonePlayer(): Player? = Bukkit.getOnlinePlayers().firstOrNull()
}
