package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.Bedwars;
import me.pycrs.bedwarsrecoded.inventory.menu.shops.GenericShop;
import me.pycrs.bedwarsrecoded.inventory.menu.shops.TeamUpgradesShop;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEntityListener implements Listener {
    private Bedwars plugin;

    public PlayerInteractEntityListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        // TODO: 4/11/2021 check if entity is an actual shopkeeper, probably gonna use persistent data container
        Entity entity = event.getRightClicked();
        if (event.getHand().equals(EquipmentSlot.HAND)) {
            switch (entity.getType()) {
                case VILLAGER:
                    new GenericShop(event.getPlayer()).open();
                    break;
                case SHEEP:
                    new TeamUpgradesShop(event.getPlayer()).open();
                    break;
            }
        }
    }
}
