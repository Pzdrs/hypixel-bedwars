package me.pycrs.bedwarsrecoded.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.pycrs.bedwarsrecoded.BTeam;
import me.pycrs.bedwarsrecoded.Bedwars;
import me.pycrs.bedwarsrecoded.Mode;
import me.pycrs.bedwarsrecoded.Utils;
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
        // TODO: 4/8/2021 spectator chat for spectators and dead players
        if (plugin.isGameInProgress()) {
            event.composer((source, displayName, message) -> Component
                    .text(Utils.formatStars(new Random().nextInt(1000)) + " ")
                    .append(Utils.getTeamPrefix(BTeam.getPlayersTeam(source)))
                    .append(displayName)
                    .append(Component.text(": "))
                    .append(message));
            if (!Bedwars.getMode().equals(Mode.SOLO)) {
                event.setCancelled(true);
                BTeam team = BTeam.getPlayersTeam(event.getPlayer());
                if (team != null) team.teamBroadcast(event.message());
            }
        } else {
            event.composer((source, displayName, message) -> Component
                    .text(Component.empty().content())
                    .append(displayName)
                    .append(Component.text(": "))
                    .append(message));
        }
    }
}
