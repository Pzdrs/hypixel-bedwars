package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.events.BWPlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BWPlayerRespawnListener implements Listener {
    private BedWars plugin;

    public BWPlayerRespawnListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(BWPlayerDeathEvent event) {
        System.out.println("player died");
    }
}
