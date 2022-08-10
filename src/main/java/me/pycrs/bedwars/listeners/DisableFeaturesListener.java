package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class DisableFeaturesListener extends BaseListener<Bedwars> {
    public DisableFeaturesListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
        event.getEntity().setFoodLevel(20);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) event.setCancelled(true);
    }

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event) {
        event.setCancelled(true);
    }
}
