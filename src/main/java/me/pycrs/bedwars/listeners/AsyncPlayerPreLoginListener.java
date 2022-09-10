package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener extends BaseListener<Bedwars> {
    public AsyncPlayerPreLoginListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        if (Bedwars.getGameStage().isGameFinished()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Component.text("This server is restarting..."));
        } else event.allow();
    }
}
