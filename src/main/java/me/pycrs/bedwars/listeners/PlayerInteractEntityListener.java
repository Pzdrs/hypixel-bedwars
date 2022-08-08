package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.menu.shops.ItemShop;
import me.pycrs.bedwars.menu.shops.TeamUpgradesShop;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEntityListener implements Listener {
    private final Bedwars plugin;

    public PlayerInteractEntityListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (event.getRightClicked() instanceof ItemFrame) event.setCancelled(true);
        if (Bedwars.isGameInProgress()) {
            // Cancel all spectator interaction
            if (BedwarsPlayer.toBPlayer(event.getPlayer()).isSpectating()) {
                event.setCancelled(true);
                return;
            }
            System.out.println(entity.getPersistentDataContainer());
            if (event.getHand().equals(EquipmentSlot.HAND)) {
                switch (entity.getType()) {
                    case VILLAGER -> new ItemShop(event.getPlayer()).open();
                    case SHEEP -> new TeamUpgradesShop(event.getPlayer()).open();
                }
            }
        }
    }
}
