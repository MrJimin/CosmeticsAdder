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

class TagItemManager(private val plugin: CosmeticsAdder) : IManager {

    private val file = File(plugin.dataFolder, "tags.yml")
    private val config: YamlConfiguration = YamlConfiguration.loadConfiguration(file.apply {
        if (!exists()) plugin.saveResource("tags.yml", false)
    })

    private val tagItems: MutableMap<String, TagItemData> = mutableMapOf()

    override fun setup() {
        tagItems.clear()
        loadFromYaml()
    }

    override fun disable() {}

    private fun loadFromYaml() {
        val section = config.getConfigurationSection("tags") ?: return

        for (key in section.getKeys(false)) {
            val path = section.getConfigurationSection(key) ?: continue

            val data = TagItemData(
                key = key,
                enabled = path.getBoolean("enabled", false),
                material = path.getString("material") ?: "PAPER",
                displayName = path.getString("display-name")?.toMiniMessage(),
                lore = path.getStringList("lore").map { it.toMiniMessage() }.toMutableList(),
                itemModel = path.getString("item-model")?.let { NamespacedKey.fromString(it) },
                tag = path.getString("tag")?.toMiniMessage() ?: continue
            )

            tagItems[key] = data
        }
    }

    fun getTagItem(id: String): ItemStack? = tagItems[id]?.toItemStack()

    fun getTagData(id: String): TagItemData? = tagItems[id]

    fun getAll(): Map<String, TagItemData> = tagItems
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
    return itemStack
}
