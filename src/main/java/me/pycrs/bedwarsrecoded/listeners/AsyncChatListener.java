package me.pycrs.bedwarsrecoded.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.pycrs.bedwarsrecoded.*;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class AsyncChatListener implements Listener {
    private Bedwars plugin;

    public AsyncChatListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        if (Bedwars.isGameInProgress()) {
            BedwarsPlayer player = BedwarsPlayer.toBPlayer(event.getPlayer());
            if (!player.isSpectating()) {
                if (Bedwars.getMode() == Mode.SOLO) {
                    // TODO: 6/14/2021 send message to all players in all teams
                } else {
                    // TODO: 6/14/2021 send message to teammates only
                }
            } else {
                // TODO: 6/14/2021 send message to spectators and dead players
            }
        } else {
            // TODO: 6/14/2021 just format it a bit and continue
        }
    }
}
