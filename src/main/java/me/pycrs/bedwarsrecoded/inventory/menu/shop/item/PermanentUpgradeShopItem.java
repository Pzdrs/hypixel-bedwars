package me.pycrs.bedwarsrecoded.inventory.menu.shop.item;

import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.item.type.TeamUpgradeItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PermanentUpgradeShopItem extends TeamUpgradeItem {
    public PermanentUpgradeShopItem(String id, Material material, int amount, BWCurrency currency, int price, String description) {
        super(id, material, amount, currency, price, description);
    }

    public PermanentUpgradeShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        super(id, itemStack, currency, price, description);
    }

    @Override
    public boolean purchase(Player player) {
        System.out.println("permanent diamond upgrade: " + id);
        return false;
    }
}
