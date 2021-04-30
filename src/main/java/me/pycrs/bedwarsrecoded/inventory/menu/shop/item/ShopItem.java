package me.pycrs.bedwarsrecoded.inventory.menu.shop.item;

import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.BWCurrency;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ShopItem {
    protected String id, description;
    protected ItemStack preview;
    protected BWCurrency currency;
    protected int price;

    public ShopItem(String id, Material material, int amount, BWCurrency currency, int price, String description) {
        this.id = id;
        this.description = description;
        this.currency = currency;
        this.price = price;
        this.preview = formatPreviewItem(material, amount);
    }

    public ShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        this.id = id;
        this.description = description;
        this.currency = currency;
        this.price = price;
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
