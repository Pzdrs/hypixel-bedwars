package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener implements Listener {
    private Bedwars plugin;

    public AsyncPlayerPreLoginListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        // TODO: 4/8/2021 create the option to spectate
        /*if (Bedwars.isGameInProgress()) {
            if (!plugin.getPlayers().contains(BedwarsPlayer.toBPlayer(Bukkit.getPlayer(event.getUniqueId())))) {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                event.kickMessage(Component.text("The game has already started.", NamedTextColor.RED));
            }
        }*/
    }
}
