package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener implements Listener {
    private BedWars plugin;

    public AsyncPlayerPreLoginListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        // TODO: 4/8/2021 create the option to spectate
        if (BedWars.gameInProgress) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.kickMessage(Component.text("The game has already started.", NamedTextColor.RED));
        }
    }
}
