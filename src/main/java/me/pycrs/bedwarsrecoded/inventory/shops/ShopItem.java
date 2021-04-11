package me.pycrs.bedwarsrecoded.inventory.shops;

import javafx.util.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopItem {
    private ItemStack preview, product;
    private Pair<BWCurrency, Integer> cost;

    public ShopItem(Material material, int amount, BWCurrency currency, int price) {
        this.preview = getPreviewItem(material);
        this.product = getFinalProduct(material, amount);
        this.cost = new Pair<>(currency, price);
    }

    private ItemStack getPreviewItem(Material material) {
        return new ItemStack(material);
    }

    private ItemStack getFinalProduct(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public ItemStack getPreview() {
        return preview;
    }

    public ItemStack getProduct() {
        return product;
    }

    public BWCurrency getCurrency() {
        return cost.getKey();
    }

    public int getPrice() {
        return cost.getValue();
    }
}
