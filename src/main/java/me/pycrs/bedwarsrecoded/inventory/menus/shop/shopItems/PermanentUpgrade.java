package me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems;

import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PermanentUpgrade extends ShopItem {
    public PermanentUpgrade(String id, ItemStack itemStack, BWCurrency currency, int price, String description, boolean teamUpgrade) {
        super(id, itemStack, currency, price, description, teamUpgrade);
    }

    @Override
    public boolean purchase(Player player) {
        return false;
    }
}
