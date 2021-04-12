package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.inventory.shops.Shop;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

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
        if (event.getClickedInventory().getHolder() instanceof Shop && event.getCurrentItem() != null && event.getInventory().getHolder() != null) {
            event.setCancelled(true);
            Shop shop = (Shop) event.getInventory().getHolder();
            ItemStack item = event.getCurrentItem();
            if (item.getItemMeta() != null) {
                String role = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "role"), PersistentDataType.STRING);
                if (role != null) {
                    switch (role) {
                        case "category":
                            System.out.println(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "category"), PersistentDataType.STRING));
                            break;
                        case "shopItem":
                            shop.handlePurchase(event);
                            break;
                    }
                }
            }
        }
    }
}
