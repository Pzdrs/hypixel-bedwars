package me.pycrs.bedwarsrecoded.inventory.shops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GenericShop extends Shop {
    public GenericShop() {
        super( true);
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void setShopItems() {
        inventory.addItem(new ItemStack(Material.WHITE_WOOL));
    }
}
