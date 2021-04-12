package me.pycrs.bedwarsrecoded.inventory.shops;

import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        ItemStack preview = new ItemStack(material);
        ItemMeta meta = preview.getItemMeta();

        meta.displayName(Component.text(ChatColor.GREEN + name));
        meta.lore(new ArrayList<>(Collections.singletonList(Component.text(Utils.color("&eClick to view!")))));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        preview.setItemMeta(meta);
        return preview;
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
