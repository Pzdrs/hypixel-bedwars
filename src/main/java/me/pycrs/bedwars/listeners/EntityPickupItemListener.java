package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.Axe;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.player.Pickaxe;
import me.pycrs.bedwars.entities.player.Sword;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class EntityPickupItemListener extends BaseListener<Bedwars> {
    public EntityPickupItemListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();

        // Outside the game, cancel
        if (!Bedwars.isGameInProgress() || Bedwars.isGameFinished() || !(event.getEntity() instanceof Player player)) {
            event.setCancelled(true);
            return;
        }

        // Cancel all spectator interaction
        if (BedwarsPlayer.isSpectating(player)) {
            event.setCancelled(true);
            return;
        }

        if (Generator.pickupCheck(plugin.getMap(), event)) return;

        // Check if the item is marked as freshly spawned
        InventoryUtils.getPersistentData(itemStack, Generator.RESOURCE_MARKER_KEY, PersistentDataType.INTEGER, resourceState -> {
            if (resourceState == Generator.ResourceState.FRESH_SPAWN.state()) {
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
        });
    }
}
