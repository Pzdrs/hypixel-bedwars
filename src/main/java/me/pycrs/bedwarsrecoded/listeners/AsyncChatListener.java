package me.pycrs.bedwarsrecoded.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class AsyncChatListener implements Listener {
    private BedWars bedWars;

    public AsyncChatListener(BedWars bedWars) {
        this.bedWars = bedWars;
        bedWars.getServer().getPluginManager().registerEvents(this, bedWars);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        if (!BedWars.isGameInProgress()) {
            event.composer((source, displayName, message) -> Component
                    .text(Utils.formatStars(new Random().nextInt(1000)) + " ")
                    .append(displayName)
                    .append(Component.text(": "))
                    .append(message));
        } else {
            event.composer((source, displayName, message) -> Component
                    .text("")
                    .append(displayName)
                    .append(Component.text(": "))
                    .append(message));
        }
    }
}
