package me.pycrs.bedwarsrecoded.inventory.menu.shop.item;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.item.dependency.Cost;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class ShopItem {
    protected String id, description;
    protected ItemStack preview;
    protected Cost cost;

    public ShopItem(String id, Material material, int amount, BWCurrency currency, int price, String description) {
        this.id = id;
        this.description = description;
        this.cost = new Cost(currency, price);
        this.preview = formatPreviewItem(material, amount);
    }

    public ShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        this.id = id;
        this.description = description;
        this.cost = new Cost(currency, price);
        this.preview = formatPreviewItem(itemStack);
    }

    private ItemStack formatPreviewItem(Material material, int amount) {
        return formatPreviewItem(new ItemStack(material, amount));
    }

    protected abstract ItemStack formatPreviewItem(ItemStack itemStack);

    public abstract boolean purchase(Player player);

    public String getId() {
        return id;
    }

    public Cost getCost() {
        return cost;
    }

    public ItemStack getPreview() {
        return preview;
    }
}
