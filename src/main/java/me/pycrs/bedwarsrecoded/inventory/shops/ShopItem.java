package me.pycrs.bedwarsrecoded.inventory.shops;

import javafx.util.Pair;
import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ShopItem {
    private String description;
    private ItemStack preview, product;
    private Pair<BWCurrency, Integer> cost;

    public ShopItem(Material material, int amount, BWCurrency currency, int price, String description) {
        this.description = description;
        this.cost = new Pair<>(currency, price);
        this.preview = formatPreviewItem(material, amount);
        this.product = getFinalProduct(material, amount);
    }

    private ItemStack formatPreviewItem(Material material, int amount) {
        return new ItemBuilder(material, amount)
                .setPlugin(BedWars.getInstance())
                .setPersistentData("role", PersistentDataType.STRING, "shopItem")
                .setLore("&7Cost: &r" + BWCurrency.formatPrice(cost), "")
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .addLoreLine("")
                .addLoreLine("&bSneak Click to remove from Quick Buy")
                .build();
    }

    public Pair<BWCurrency, Integer> getCost() {
        return cost;
    }

    private ItemStack getFinalProduct(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public ItemStack getPreview() {
        return preview;
    }
}
