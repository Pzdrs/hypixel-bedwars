package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.GenericShop;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.TeamUpgradesShop;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {
    private BedWars plugin;

    public PlayerInteractEntityListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {
        // TODO: 4/11/2021 check if entity is an actual shopkeeper, probably gonna use persistent data container
        Entity entity = event.getRightClicked();
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
