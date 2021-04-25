package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.inventory.menus.Menu;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.MenuButtonHandler;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.Shop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

public class InventoryClickListener implements Listener {
    private BedWars plugin;
    public static Map<String, MenuButtonHandler> menuButtons = new HashMap<>();

    public InventoryClickListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof Menu) {
            event.setCancelled(true);
            // Check if the player has clicked on a custom menu button

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
