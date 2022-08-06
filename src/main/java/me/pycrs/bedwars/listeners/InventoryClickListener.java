package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.Menu;
import me.pycrs.bedwars.menu.shops.Shop;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.MenuUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class InventoryClickListener implements Listener {
    private Bedwars plugin;

    public InventoryClickListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.getSlot() == 40) {
            event.setCancelled(true);
        } else if (event.getInventory().getHolder() instanceof Menu) {
            event.setCancelled(true);
            Shop shop = (Shop) event.getInventory().getHolder();
            if (event.getClickedInventory() != null) {
                // Ignore clicks in player's own inventory, null items and roleless items
                if (!(event.getClickedInventory().getHolder() instanceof Menu) || event.getCurrentItem() == null || !MenuUtils.hasRole(event.getCurrentItem()))
                    return;
                // Check if the player has clicked on a custom menu button
                Optional<String> potentialMenuButtonID = InventoryUtils
                        .getPersistentData(event.getCurrentItem(), new NamespacedKey(plugin, "menuButtonID"), PersistentDataType.STRING);
                if (potentialMenuButtonID.isPresent()) {
                    Menu menu = (Menu) event.getInventory().getHolder();
                    menu.getButtons().get(potentialMenuButtonID.get()).getHandler().handle();
                    return;
                }
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
