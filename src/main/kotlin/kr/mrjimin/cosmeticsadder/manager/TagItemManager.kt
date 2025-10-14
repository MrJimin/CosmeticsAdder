package kr.mrjimin.cosmeticsadder.manager

import kr.mrjimin.cosmeticsadder.CosmeticsAdder
import kr.mrjimin.cosmeticsadder.item.ItemHandler
import kr.mrjimin.cosmeticsadder.item.TagItemData
import kr.mrjimin.throttlecore.util.component.toMiniMessage
import kr.mrjimin.throttlecore.util.component.toPlain
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.io.File

class TagItemManager(plugin: CosmeticsAdder) : IManager {

    private val file = File(plugin.dataFolder, "tags.yml")
    private val config: YamlConfiguration = YamlConfiguration.loadConfiguration(file.apply {
        if (!exists()) plugin.saveResource("tags.yml", false)
    })

    val tagItems = mutableMapOf<String, TagItemData>()

    override fun setup() {
        tagItems.clear()
        config.getConfigurationSection("tags")?.getKeys(false)?.forEach { key ->
            val path = config.getConfigurationSection("tags.$key") ?: return@forEach

            tagItems[key] = TagItemData(
                key,
                path.getBoolean("enabled", false),
                path.getString("material") ?: "PAPER",
                path.getString("display-name")?.toMiniMessage(),
                path.getStringList("lore").map { it.toMiniMessage() }.toMutableList(),
                NamespacedKey.fromString(path.getString("item-model") ?: ""),
                path.getString("tag")!!.toMiniMessage()
            )
        }
    }

    override fun disable() {}

    fun getTagItem(id: String): TagItemData? = tagItems[id]
}

fun TagItemData.toItemStack(): ItemStack {
    val itemStack = ItemHandler.itemStackFromKey(material)
        ?: throw IllegalArgumentException("Invalid material: $material")

    itemStack.editMeta {
        it.displayName(displayName?.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE))
        it.lore(lore)
        it.itemModel = itemModel
        it.persistentDataContainer.apply {
            set(ItemHandler.KEY, PersistentDataType.STRING, key)
            set(ItemHandler.TAG, PersistentDataType.STRING, tag.toPlain())
        }
    }

    // itemModel?.let { itemStack.setData(DataComponentTypes.ITEM_MODEL, it) }

    return itemStack
}