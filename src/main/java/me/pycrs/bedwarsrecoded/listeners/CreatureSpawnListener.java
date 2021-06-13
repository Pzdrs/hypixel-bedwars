package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.Bedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {
    private Bedwars plugin;

    public CreatureSpawnListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        // FIXME: 4/8/2021 When i eventually add dragons, this is gonna need to be edited
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) event.setCancelled(true);
    }
}
