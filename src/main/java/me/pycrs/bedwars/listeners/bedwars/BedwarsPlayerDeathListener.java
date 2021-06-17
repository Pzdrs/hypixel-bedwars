package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Utils;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import me.pycrs.bedwars.events.BedwarsPlayerRespawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class BedwarsPlayerDeathListener implements Listener {
    private Bedwars plugin;

    public BedwarsPlayerDeathListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(BedwarsPlayerDeathEvent event) {
        Player player = event.getBPlayer().getPlayer();
        event.getBPlayer().setSpectator(true);
        player.teleport(plugin.getMap().getLobbySpawn());
        if (event.getBPlayer().getTeam().hasBed()) {
            Bukkit.getServer().getPluginManager().callEvent(new BedwarsPlayerRespawnEvent(event.getBPlayer()));
        } else {
            // TODO: 6/17/2021 game over and all that shit
        }
    }
}
