package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;

public class EntityPickupItemListener implements Listener {
    private Bedwars plugin;

    public EntityPickupItemListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!Bedwars.isGameInProgress() || !(event.getEntity() instanceof Player player)) {
            event.setCancelled(true);
            return;
        }
        if (Generator.pickupCheck(plugin.getMap(), event)) return;

        if (event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "resourceState"), PersistentDataType.INTEGER) &&
                event.getItem().getItemStack().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "resourceState"), PersistentDataType.INTEGER) == 0) {
            // Cancel the event - the item's state needs to be changed first
            event.setCancelled(true);
            ItemStack pickedUpItemStack = new ItemBuilder(event.getItem().getItemStack())
                    .setPersistentData("resourceState", PersistentDataType.INTEGER, 1)
                    .build();

            player.getInventory().addItem(pickedUpItemStack);

            // Split
            for (Entity entity : player.getWorld().getNearbyLivingEntities(event.getItem().getLocation(), 2)) {
                if (entity instanceof Player possibleSplit && !possibleSplit.getUniqueId().equals(player.getUniqueId())) {
                    if (possibleSplit.getLocation().distance(event.getItem().getLocation()) <= 2)
                        possibleSplit.getInventory().addItem(pickedUpItemStack);
                }
            }
        }
        // Cancel all spectator interaction
        if (BedwarsPlayer.toBPlayer(player).isSpectating()) {
            event.setCancelled(true);
        }
    }
}
