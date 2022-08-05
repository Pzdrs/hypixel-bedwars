package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.util.Utils;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import me.pycrs.bedwars.events.BedwarsPlayerRespawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class BedwarsPlayerDeathListener implements Listener {
    private Bedwars plugin;

    public BedwarsPlayerDeathListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(BedwarsPlayerDeathEvent event) {
        Player player = event.getPlayer();

        event.getBedwarsPlayer().setSpectator(true);
        player.teleport(plugin.getMap().getLobbySpawn());

        if (event.gotKilled()) {
            event.setMessage(BedwarsPlayerDeathEvent.DeathMessage.getMessage(event.getCause(), event.getBedwarsPlayer(), event.getBedwarsKiller()));
        } else {
            event.setMessage(BedwarsPlayerDeathEvent.DeathMessageNatural.getMessage(event.getCause(), event.getBedwarsPlayer()));
        }

        if (event.getBedwarsPlayer().getTeam().hasBed()) {
            Bukkit.getServer().getPluginManager().callEvent(new BedwarsPlayerRespawnEvent(event.getBedwarsPlayer()));
        } else {
            // Final kill message
            event.setMessage(event.getMessage().append(Component.text(" FINAL KILL!", Style.style(NamedTextColor.AQUA, TextDecoration.BOLD))));
            event.getBedwarsPlayer().getPlayer().sendMessage(Component.text("You have been eliminated!", NamedTextColor.RED));
            event.getBedwarsPlayer().getTeam().eliminatePlayer(event.getBedwarsPlayer());
        }

        // Broadcast the death message
        Utils.inGameBroadcast(event.getMessage());
    }
}
