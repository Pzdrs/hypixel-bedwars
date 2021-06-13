package me.pycrs.bedwarsrecoded.inventory.menu.shops.dependency;

import me.pycrs.bedwarsrecoded.Bedwars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menu.shops.items.ShopItem;
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
                .setPlugin(Bedwars.getInstance())
                .setDisplayName(ChatColor.GREEN + name + ChatColor.RESET)
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
