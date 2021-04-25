package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TeamUpgradesShop extends Shop{
    public TeamUpgradesShop(Player player) {
        super(player);
    }

    @Override
    protected int getSize() {
        return 45;
    }

    @Override
    protected String getDefaultCategory() {
        return "default";
    }

    @Override
    protected void setCategories() {
        categories.add(new ShopCategory("default", "Upgrades & Traps", Material.SPONGE));
        categories.add(new ShopCategory("traps", "Queue a trap", Material.SPONGE));
    }

    @Override
    protected void handlePurchase(InventoryClickEvent event) {

    }
}
