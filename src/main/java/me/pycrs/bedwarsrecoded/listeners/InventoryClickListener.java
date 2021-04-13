package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.inventory.menus.Menu;
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
        if (event.getInventory().getHolder() instanceof Menu && event.getClickedInventory().getHolder() != null) {
            event.setCancelled(true);
            Shop menu = ((Shop)event.getInventory().getHolder());
            if (event.getClickedInventory() == null) {
                menu.cycleCategory(Shop.CategoryCycleDirection.RIGHT);
            } else {
                menu.handle(event);
            }
        }
    }
}
