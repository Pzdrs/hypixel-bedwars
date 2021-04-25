package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TrapsShop extends Shop{
    public TrapsShop(Player player) {
        super(player);
    }

    @Override
    protected int getSize() {
        return 27;
    }

    @Override
    protected String getDefaultCategory() {
        return "default";
    }

    @Override
    protected void setCategories() {
        categories.add(new ShopCategory("default", "Queue a trap", Material.SPONGE));
    }

    @Override
    protected void handlePurchase(InventoryClickEvent event) {
        System.out.println("trap purchased");
    }
}
