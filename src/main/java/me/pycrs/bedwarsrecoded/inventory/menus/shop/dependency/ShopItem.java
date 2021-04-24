package me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.MenuUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ShopItem {
    private String id, description;
    private ItemStack preview, product;
    private BWCurrency currency;
    private int price;

    public ShopItem(String id, Material material, int amount, BWCurrency currency, int price, String description) {
        this.id = id;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.product = formatProductItem(material, amount);
        this.preview = formatPreviewItem(material, amount);
    }

    public ShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        this.id = id;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.product = formatProductItem(itemStack);
        this.preview = formatPreviewItem(itemStack);
    }

    private ItemStack formatProductItem(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    private ItemStack formatProductItem(ItemStack itemStack) {
        return new ItemStack(itemStack);
    }

    private ItemStack formatPreviewItem(Material material, int amount) {
        return formatPreviewItem(new ItemStack(material, amount));
    }

    private ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setPlugin(BedWars.getInstance())
                .setPersistentData("role", PersistentDataType.STRING, "shopItem")
                .setPersistentData("itemId", PersistentDataType.STRING, id)
                .setLore("&7Cost: &r" + BWCurrency.formatPrice(currency, price), "")
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .addLoreLine("&bSneak Click to remove from Quick Buy&r")
                .build();
    }

    public boolean purchase(Player player) {
        // TODO: 4/17/2021 add sounds
        if (player.getInventory().firstEmpty() == -1) {
            // Inventory full
            player.sendMessage(Component.text("Purchase Failed! Your inventory is full!", NamedTextColor.RED));
            return false;
        }
        int amount = MenuUtils.canAffordAmount(currency, price, player);
        if (amount != 0) {
            // Cannot afford the item
            player.sendMessage(Component.text("You don't have enough " + WordUtils.capitalize(currency.name().toLowerCase()) + "! Need " + amount + " more!", NamedTextColor.RED));
            return false;
        }
        player.sendMessage(Component.text("You purchased ", NamedTextColor.GREEN)
                .append(Objects.requireNonNull(preview.getItemMeta().displayName()).color(NamedTextColor.GOLD)));
        player.getInventory().removeItem(new ItemStack(currency.getType(), price));
        player.getInventory().addItem(product);
        return true;
    }

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

    @Override
    public String toString() {
        return "ShopItem{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", preview=" + preview +
                ", product=" + product +
                ", currency=" + currency +
                ", price=" + price +
                '}';
    }
}
