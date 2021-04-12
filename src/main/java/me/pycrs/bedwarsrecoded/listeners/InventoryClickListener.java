package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.inventory.shops.Shop;
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
        if (event.getClickedInventory() == null) {
            // TODO: 4/12/2021 make categories scrollable
            return;
        }
        if (event.getClickedInventory().getHolder() instanceof Shop && event.getCurrentItem() != null) {
            event.setCancelled(true);
            Shop shop = (Shop) event.getInventory().getHolder();
            shop.handlePurchase(event);
        }
    }
}
