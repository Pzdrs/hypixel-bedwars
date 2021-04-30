package me.pycrs.bedwarsrecoded.inventory.menu.shop.item.type;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.item.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class CommonItem extends ShopItem {
    public CommonItem(String id, Material material, int amount, BWCurrency currency, int price, String description) {
        super(id, material, amount, currency, price, description);
    }

    public CommonItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        super(id, itemStack, currency, price, description);
    }

    @Override
    protected final ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setPlugin(BedWars.getInstance())
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData("role", PersistentDataType.STRING, "shopItem")
                .setPersistentData("itemId", PersistentDataType.STRING, id)
                .setLore("&7Cost: &r" + BWCurrency.formatPrice(currency, price), "")
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .build();
    }
}
