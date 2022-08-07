package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.events.*;
import me.pycrs.bedwars.util.Utils;
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
    private final Bedwars plugin;

    public BedwarsPlayerDeathListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(BedwarsPlayerDeathEvent event) {
        // Death message
        event.setMessage(BedwarsPlayerDeathEvent.DeathMessage.getMessage(event.getCause(), event.getBedwarsPlayer()));

        // Common player death logic
        onPlayerDeath(event.getBedwarsPlayer(), plugin, event);
    }

    public static void onPlayerDeath(BedwarsPlayer deadPlayer, Bedwars plugin, BedwarsEventWithMessage event) {
        deadPlayer.setSpectator(true);
        deadPlayer.getPlayer().teleport(plugin.getMap().getLobbySpawn());

        // Statistics
        deadPlayer.getStatistics().addDeath();

        // If the player still has a bed, respawn them, otherwise eliminate them
        if (deadPlayer.getTeam().hasBed()) {
            Bukkit.getServer().getPluginManager().callEvent(new BedwarsPlayerRespawnEvent(plugin, deadPlayer));
        } else {
            // Final kill message
            event.setMessage(event.getMessage().append(Component.text(" FINAL KILL!", Style.style(NamedTextColor.AQUA, TextDecoration.BOLD))));
            deadPlayer.getPlayer().sendMessage(Component.text("You have been eliminated!", NamedTextColor.RED));
            deadPlayer.getTeam().eliminatePlayer(deadPlayer);
        }

        // Broadcast the death message
        Utils.inGameBroadcast(event.getMessage());
    }
}
