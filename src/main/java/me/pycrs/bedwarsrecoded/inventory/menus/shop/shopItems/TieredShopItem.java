package me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TieredShopItem {
    private String id, description;
    private ItemStack preview;
    private Map<ShopItemTier, Boolean> tiers;

    public TieredShopItem(String id, ItemStack preview, String description, ShopItemTier... tiers) {
        this.id = id;
        this.description = description;
        this.preview = preview;
        this.tiers = new HashMap<>();
        for (ShopItemTier tier : tiers) {
            this.tiers.put(tier, false);
        }
    }
}
