package me.pycrs.bedwarsrecoded.inventory.shops;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Shop implements InventoryHolder {
    protected Inventory inventory;
    private Map<ShopCategory, Boolean> categories;
    private List<ShopItem> items;

    public Shop(boolean categorical) {
        if (categorical) this.categories = new HashMap<>();
        this.items = new ArrayList<>();
        setShopItems();
        injectItems(categorical);
    }

    private void injectItems(boolean categorical) {
        if (categorical) {

        } else {
            items.forEach(shopItem -> inventory.addItem(shopItem.getPreview()));
        }
    }

    public abstract int getSize();

    public void setShopItems() {

    }

    public void show(Player player) {
        this.inventory = Bukkit.createInventory(this, getSize());
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
