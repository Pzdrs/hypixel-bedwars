package me.pycrs.bedwars.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Mode;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.player.BedwarsPlayerList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;

import java.util.Optional;

public class AsyncChatListener extends BaseListener<Bedwars> {
    public AsyncChatListener(Bedwars plugin) {
        super(plugin);
    }

    // TODO: 8/10/2022 rework to use audience
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        if (!Bedwars.getGameStage().isGameInProgress()) {
            // Send to everyone
            event.renderer((source, sourceDisplayName, message, viewer) -> Component.empty()
                    .append(sourceDisplayName)
                    .append(Component.text(": ", NamedTextColor.WHITE))
                    .append(message));
        } else {
            Optional<BedwarsPlayer> potentialBedwarsSender = BedwarsPlayer.of(event.getPlayer());
            if (potentialBedwarsSender.isEmpty() || potentialBedwarsSender.get().isSpectating()) {
                // The player hasn't been there when the game started, or they're spectating
                event.setCancelled(true);
                BedwarsPlayerList.getList().spectators()
                        .forEach(bedwarsPlayer -> bedwarsPlayer.getPlayer().sendMessage(
                                Component.text("[SPECTATOR] ", NamedTextColor.GRAY)
                                        .append(event.getPlayer().displayName())
                                        .append(Component.text(": ", NamedTextColor.WHITE))
                                        .append(event.message().color(NamedTextColor.WHITE))
                        ));
            } else {
                // The player has been there when the game started
                BedwarsPlayer bedwarsSender = potentialBedwarsSender.get();
                if (Bedwars.getMode() == Mode.SOLO) {
                    event.renderer((source, sourceDisplayName, message, viewer) -> Component.empty()
                            .append(bedwarsSender.getLevel().toComponent())
                            .append(Component.space())
                            .append(sourceDisplayName)
                            .append(Component.text(": ", NamedTextColor.WHITE))
                            .append(message));
                } else {
                    // Send to team members and spectators
                    event.setCancelled(true);
                    BedwarsPlayerList.getList()
                            .filter(bedwarsPlayer -> bedwarsPlayer.isSpectating() || bedwarsPlayer.getTeam().isPartOfTeam(bedwarsSender))
                            .forEach(bedwarsPlayer -> bedwarsPlayer.getPlayer().sendMessage(Component.text()
                                    .append(bedwarsSender.getLevel().toComponent())
                                    .append(Component.space())
                                    .append(event.getPlayer().displayName())
                                    .append(Component.text(": ", NamedTextColor.WHITE))
                                    .append(event.message()).build())
                            );
                }
            }
        }
    }
}
