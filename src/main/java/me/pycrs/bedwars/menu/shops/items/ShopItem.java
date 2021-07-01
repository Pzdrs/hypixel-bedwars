package me.pycrs.bedwars.menu.shops.items;

import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.menu.shops.items.dependency.Cost;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
