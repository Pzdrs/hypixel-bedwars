package me.pycrs.bedwars.listeners;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.BedwarsPlayer;
import me.pycrs.bedwars.Mode;
import me.pycrs.bedwars.Utils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class AsyncChatListener implements Listener {
    private Bedwars plugin;

    public AsyncChatListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        System.out.println(event.viewers());

        if (Bedwars.isGameInProgress()) {
            BedwarsPlayer player = BedwarsPlayer.toBPlayer(event.getPlayer());
            if (!player.isSpectating()) {
                if (Bedwars.getMode() == Mode.SOLO) {
                    // Send to everyone
                    plugin.getServer().sendMessage(Component.empty()
                            .append(event.getPlayer().displayName())
                            .append(Component.text(": ", NamedTextColor.WHITE))
                            .append(event.message()));
                } else {
                    // Send to team members and spectators
                    event.setCancelled(true);
                    plugin.getPlayers().forEach(bedwarsPlayer -> {
                        if (bedwarsPlayer.isSpectating() || bedwarsPlayer.getTeam().isPartOfTeam(player))
                            bedwarsPlayer.getPlayer().sendMessage(Component.empty()
                                    .append(event.getPlayer().displayName())
                                    .append(Component.text(": ", NamedTextColor.WHITE))
                                    .append(event.message()));
                    });
                }
            } else {
                // Send to all spectators (include players that are respawning)
                // TODO: 6/14/2021 send message to spectators and dead players
            }
        } else {
            // Send to everyone
            event.renderer((source, sourceDisplayName, message, viewer) -> Component.empty()
                    .append(sourceDisplayName)
                    .append(Component.text(": ", NamedTextColor.WHITE))
                    .append(message));
        }
    }
}
