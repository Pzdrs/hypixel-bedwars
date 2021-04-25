package me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class ShopItem {
    private String id, description;
    protected boolean teamUpgrade;
    protected ItemStack preview;
    protected BWCurrency currency;
    protected int price;

    public ShopItem(String id, Material material, int amount, BWCurrency currency, int price, String description, boolean teamUpgrade) {
        this.id = id;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.preview = formatPreviewItem(material, amount);
        this.teamUpgrade = teamUpgrade;
    }

    public ShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description, boolean teamUpgrade) {
        this.id = id;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.teamUpgrade = teamUpgrade;
        this.preview = formatPreviewItem(itemStack);
    }

    private ItemStack formatPreviewItem(Material material, int amount) {
        return formatPreviewItem(new ItemStack(material, amount));
    }

    private ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setPlugin(BedWars.getInstance())
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData("role", PersistentDataType.STRING, "shopItem")
                .setPersistentData("itemId", PersistentDataType.STRING, id)
                .setLore("&7Cost: &r" + BWCurrency.formatPrice(currency, price), "")
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .addLoreLine(teamUpgrade ? null : "&bSneak Click to remove from Quick Buy&r")
                .previewEnchantments()
                .build();
    }

    public abstract boolean purchase(Player player);

    public String getId() {
        return id;
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
