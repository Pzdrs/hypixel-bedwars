package me.pycrs.bedwarsrecoded.inventory.shops;

import me.pycrs.bedwarsrecoded.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ShopCategory {
    private String name;
    private ItemStack preview;
    private List<ShopItem> items;

    public ShopCategory(String name, Material preview, ShopItem... items) {
        this.name = name;
        this.preview = formatPreviewItem(preview);
        this.items = Arrays.asList(items);
    }

    private ItemStack formatPreviewItem(Material material) {
        return new ItemBuilder(material)
                .setDisplayName(ChatColor.GREEN + name)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setLore("&eClick to view!")
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
}
