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
                    // TODO: 6/14/2021 send message to all players in all teams
                } else {
                    event.setCancelled(true);
                    player.getTeam().getPlayers().forEach(bedwarsPlayer -> {
                        bedwarsPlayer.getPlayer().sendMessage(Component.empty()
                                .append(event.getPlayer().displayName())
                                .append(Component.text(": ", NamedTextColor.WHITE))
                                .append(event.message()));
                    });
                }
            } else {
                // TODO: 6/14/2021 send message to spectators and dead players
            }
        } else {
            event.renderer((source, sourceDisplayName, message, viewer) -> Component.empty()
                    .append(sourceDisplayName)
                    .append(Component.text(": ", NamedTextColor.WHITE))
                    .append(message));
        }
    }
}
