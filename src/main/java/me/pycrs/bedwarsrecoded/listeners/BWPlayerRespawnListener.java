package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.events.BWPlayerDeathEvent;
import me.pycrs.bedwarsrecoded.events.BWPlayerRespawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Duration;

public class BWPlayerRespawnListener implements Listener {
    private BedWars plugin;

    public BWPlayerRespawnListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerRespawn(BWPlayerRespawnEvent event) {
        Player player = event.getPlayer().getPlayer();

        player.setInvulnerable(false);
        player.setInvisible(false);
        player.setAllowFlight(false);

        System.out.println(player.isDead());
        player.teleport(player.getLocation().set(150, 63, -200));
        player.showTitle(Title.title(Component.text("RESPAWNED!", NamedTextColor.GREEN), Component.empty(),
                Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
        player.setGameMode(GameMode.SURVIVAL);
        // TODO: 4/8/2021 teleport to base
    }
}
