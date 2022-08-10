package me.pycrs.bedwars.menu.shops.dependency;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.menu.shops.items.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
                .setDisplayName(ChatColor.GREEN + name + ChatColor.RESET)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES)
                .setPersistentData(ItemBuilder.ROLES_KEY, PersistentDataType.STRING, "category")
                .setPersistentData(new NamespacedKey(Bedwars.getInstance(), "category"), PersistentDataType.STRING, id)
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
