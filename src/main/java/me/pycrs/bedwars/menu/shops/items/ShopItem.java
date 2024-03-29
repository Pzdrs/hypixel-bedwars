package me.pycrs.bedwars.menu.shops.items;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.menu.shops.items.dependency.Cost;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ShopItem {
    public enum Role {
        DEFAULT("shopItem"),
        PERSISTENT_EQUIPMENT("persistent_equipment");

        private final String key;

        Role(String key) {
            this.key = key;
        }

        public String key() {
            return key;
        }
    }

    public static final NamespacedKey ITEM_ID_KEY = new NamespacedKey(Bedwars.getInstance(), "itemId");
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
