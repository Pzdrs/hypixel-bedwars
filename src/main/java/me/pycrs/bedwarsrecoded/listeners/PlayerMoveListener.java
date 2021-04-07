package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.events.BWPlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private BedWars plugin;

    public PlayerMoveListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().getY() < 0 && BedWars.gameInProgress) {
            Location location = player.getLocation();
            location.setY(100);
            event.getPlayer().teleport(location);
            event.getPlayer().getLocation().setY(150);
            Bukkit.getPluginManager().callEvent(new BWPlayerDeathEvent(plugin, event.getPlayer()));
        }
    }
}
