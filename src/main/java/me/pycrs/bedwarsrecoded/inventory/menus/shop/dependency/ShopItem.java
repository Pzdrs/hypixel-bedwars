package me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ShopItem {
    private String description;
    private ItemStack preview, product;
    private BWCurrency currency;
    private int price;

    public ShopItem(Material material, int amount, BWCurrency currency, int price, String description) {
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.product = new ItemStack(material, amount);
        this.preview = formatPreviewItem(material, amount);
    }

    public ShopItem(ItemStack product, BWCurrency currency, int price, String description) {
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.product = product;
        this.preview = formatPreviewItem(product);
    }

    private ItemStack formatPreviewItem(Material material, int amount) {
        return formatPreviewItem(new ItemStack(material, amount));
    }

    private ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setPlugin(BedWars.getInstance())
                .setPersistentData("role", PersistentDataType.STRING, "shopItem")
                .setLore("&7Cost: &r" + BWCurrency.formatPrice(currency, price), "")
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .addLoreLine("")
                .addLoreLine("&bSneak Click to remove from Quick Buy&r")
                .build();
    }

    public BWCurrency getCurrency() {
        return currency;
    }

    public int getPrice() {
        return price;
    }

    public ItemStack getPreview() {
        return preview;
    }
}
