package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.inventory.menus.Menu;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.Shop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    private BedWars plugin;

    public InventoryClickListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            event.setCancelled(true);
            Shop shop = (Shop) event.getInventory().getHolder();
            if (event.getClickedInventory() != null) {
                // Ignore clicks in player's own inventory, null items and roleless items
                if (!(event.getClickedInventory().getHolder() instanceof Menu) || event.getCurrentItem() == null || !MenuUtils.hasRole(event.getCurrentItem()))
                    return;
                shop.handle(event);
            } else {
                // Category cycling
                if (event.getClick().isLeftClick()) {
                    shop.cycleCategory(Shop.CategoryCycleDirection.LEFT);
                } else if (event.getClick().isRightClick()) {
                    shop.cycleCategory(Shop.CategoryCycleDirection.RIGHT);
                }
            }
        }
    }
}
