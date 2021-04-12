package me.pycrs.bedwarsrecoded.inventory.shops;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ShopCategory {
    private String id, name;
    private ItemStack preview;
    private List<ShopItem> items;

    public ShopCategory(String id, String name, Material preview, ShopItem... items) {
        this.id = id;
        this.name = name;
        this.preview = formatPreviewItem(preview);
        this.items = Arrays.asList(items);
    }

    private ItemStack formatPreviewItem(Material material) {
        return new ItemBuilder(material)
                .setPlugin(BedWars.getInstance())
                .setDisplayName(ChatColor.GREEN + name)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setPersistentData("role", PersistentDataType.STRING, "category")
                .setPersistentData("category", PersistentDataType.STRING, id)
                .build();
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public ItemStack getPreview() {
        return preview;
    }

    public String getId() {
        return id;
    }
}
