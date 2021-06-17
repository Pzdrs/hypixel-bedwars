package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsPlayerKillListener implements Listener {
    private Bedwars plugin;

    public BedwarsPlayerKillListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerKill(BedwarsPlayerKillEvent event) {

    }
}
