package me.pycrs.bedwars.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.Mode;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
            if (player == null || player.isSpectating()) {
                event.setCancelled(true);
                plugin.getPlayers().stream()
                        .filter(BedwarsPlayer::isSpectating)
                        .forEach(bedwarsPlayer -> bedwarsPlayer.getPlayer().sendMessage(Component.text("[SPECTATOR] ", NamedTextColor.GRAY)
                                .append(event.getPlayer().displayName())
                                .append(Component.text(": ", NamedTextColor.WHITE))
                                .append(event.message().color(NamedTextColor.WHITE))));
            } else if (!player.isSpectating()) {
                // TODO: 6/16/2021 add bw level 
                if (Settings.mode == Mode.SOLO) {
                    // Send to everyone
                    event.setCancelled(true);
                    plugin.getServer().sendMessage(Component.empty()
                            .append(Utils.formatStars(player.getLevel()))
                            .append(event.getPlayer().displayName())
                            .append(Component.text(": ", NamedTextColor.WHITE))
                            .append(event.message()));
                } else {
                    // Send to team members and spectators
                    event.setCancelled(true);
                    plugin.getPlayers().forEach(bedwarsPlayer -> {
                        if (bedwarsPlayer.isSpectating() || bedwarsPlayer.getTeam().isPartOfTeam(player))
                            bedwarsPlayer.getPlayer().sendMessage(Component.empty()
                                    .append(Utils.formatStars(player.getLevel()))
                                    .append(event.getPlayer().displayName())
                                    .append(Component.text(": ", NamedTextColor.WHITE))
                                    .append(event.message()));
                    });
                }
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
