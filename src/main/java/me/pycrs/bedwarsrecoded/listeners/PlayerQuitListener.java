package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private BedWars bedWars;

    public PlayerQuitListener(BedWars bedWars) {
        this.bedWars = bedWars;
        bedWars.getServer().getPluginManager().registerEvents(this, bedWars);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        // Get rid of the default join message
        event.quitMessage(null);

        Player player = event.getPlayer();

        // New quit messages
        bedWars.getServer().sendMessage(player.displayName()
                .append(Component.text(" has quit! ", NamedTextColor.YELLOW)));

        if (Bukkit.getOnlinePlayers().size() - 1 < bedWars.getMode().getMinPlayers() &&
                bedWars.playerJoinEvent.countDown != null &&
                !bedWars.playerJoinEvent.countDown.isCancelled()) {
            bedWars.playerJoinEvent.countDown.cancel();
            Utils.inGameBroadcast(Component.text("We don't have enough players! Start cancelled.", NamedTextColor.RED));
            // TODO: 4/6/2021 Title Waiting for more players... in red color
        }
    }
}
