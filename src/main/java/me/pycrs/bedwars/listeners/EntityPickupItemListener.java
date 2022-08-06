package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.util.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class EntityPickupItemListener implements Listener {
    private final Bedwars plugin;

    public EntityPickupItemListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();

        if (!Bedwars.isGameInProgress() || !(event.getEntity() instanceof Player player)) {
            event.setCancelled(true);
            return;
        }

        if (Generator.pickupCheck(plugin.getMap(), event)) return;

        // Check if the item is marked as freshly spawned
        Optional<Integer> resourceState = Utils.getPersistentData(itemStack, Generator.RESOURCE_MARKER_KEY, PersistentDataType.INTEGER);
        if (resourceState.isPresent() && resourceState.get() == Generator.ResourceState.FRESH_SPAWN.state()) {
            // Mark the item as picked up
            ItemStack pickedUpItemStack = new ItemBuilder(itemStack)
                    .setPersistentData(Generator.RESOURCE_MARKER_KEY, PersistentDataType.INTEGER, Generator.ResourceState.PICKED_UP.state())
                    .build();

            // Give the marked item to the player instead of the original one
            event.getItem().setItemStack(pickedUpItemStack);

            // Split with all nearby players
            for (Entity entity : player.getWorld().getNearbyLivingEntities(event.getItem().getLocation(), Settings.forgeSplitRadius)) {
                if (entity instanceof Player possibleSplit && !possibleSplit.getUniqueId().equals(player.getUniqueId()))
                    possibleSplit.getInventory().addItem(pickedUpItemStack);
            }
        }

        // Cancel all spectator interaction
        if (BedwarsPlayer.toBPlayer(player).isSpectating()) {
            event.setCancelled(true);
        }
    }
}
