package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Duration;

public class PlayerQuitListener implements Listener {
    private BedWars plugin;

    public PlayerQuitListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        // Get rid of the default join message
        event.quitMessage(null);

        Player player = event.getPlayer();

        // New quit messages
        if (BedWars.gameInProgress) {
            plugin.getServer().sendMessage(Component.text(player.getName(), plugin.getBPlayer(player).getTeam().getTeamColor().getColor())
                    .append(Component.text(" disconnected", NamedTextColor.GRAY)));
        } else {
            plugin.getServer().sendMessage(player.displayName()
                    .append(Component.text(" has quit! ", NamedTextColor.YELLOW)));
        }

        if (Bukkit.getOnlinePlayers().size() - 1 < BedWars.getMode().getMinPlayers() && Utils.isLobbyCountdownInProgress(plugin)) {
            plugin.getLobbyLoop().cancel();
            plugin.getServer().playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT, Sound.Source.BLOCK, 1f, 1f));
            Utils.inGameBroadcast(Component.text("We don't have enough players! Start cancelled.", NamedTextColor.RED));
            Bukkit.getServer().showTitle(Title.title(Component.text("Waiting for more players...", NamedTextColor.RED), Component.text().asComponent(),
                    Title.Times.of(Duration.ZERO, Duration.ofMillis(2000), Duration.ZERO)));
        }
    }
}
