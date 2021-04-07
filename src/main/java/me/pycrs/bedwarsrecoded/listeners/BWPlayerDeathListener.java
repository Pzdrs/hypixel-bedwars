package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.events.BWPlayerRespawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BWPlayerDeathListener implements Listener {
    private BedWars plugin;

    public BWPlayerDeathListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(BWPlayerRespawnEvent event) {
        System.out.println("player respawned");
    }
}
