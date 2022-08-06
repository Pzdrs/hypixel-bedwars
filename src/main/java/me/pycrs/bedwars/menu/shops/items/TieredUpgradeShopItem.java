package me.pycrs.bedwars.menu.shops.items;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.menu.shops.items.dependency.ShopItemTier;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class TieredUpgradeShopItem extends ShopItem {
    private Map<ShopItemTier, Boolean> tiers;

    public TieredUpgradeShopItem(String id, ItemStack preview, String description, int startTier, ShopItemTier... tiers) {
        super(id, preview, getNextTierCurrency(), getNextTierPrice(), description);

        this.tiers = new HashMap<>();
        for (int i = 0; i < tiers.length; i++) {
            ShopItemTier tier = tiers[i];
            System.out.println(tier);
            if (i >= startTier) {
                this.tiers.put(tier, false);
                continue;
            }
            this.tiers.put(tier, true);
        }
    }

    private static BWCurrency getNextTierCurrency() {
        return BWCurrency.DIAMOND;
    }

    private static int getNextTierPrice() {
        return 1;
    }

    // TODO: 4/30/2021 put the tiers in the lore
    @Override
    protected ItemStack formatPreviewItem(ItemStack itemStack) {
        System.out.println(tiers);
        return new ItemBuilder(itemStack)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData(ShopItem.ROLE_KEY, PersistentDataType.STRING, "shopItem")
                .setPersistentData(ShopItem.ITEM_ID_KEY, PersistentDataType.STRING, id)
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .build();
    }

    @Override
    public boolean purchase(Player player) {
        System.out.println("clicked tiered item");
        return false;
    }
}
