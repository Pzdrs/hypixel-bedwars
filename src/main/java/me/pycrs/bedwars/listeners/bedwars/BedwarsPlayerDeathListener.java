package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Utils;
import me.pycrs.bedwars.events.BWPlayerDeathEvent;
import me.pycrs.bedwars.events.BWPlayerRespawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class BedwarsPlayerDeathListener implements Listener {
    private Bedwars plugin;
    private AtomicInteger respawnTimer;

    public BedwarsPlayerDeathListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(BWPlayerDeathEvent event) {
        Player player = event.getBPlayer().getPlayer();
        event.getBPlayer().setSpectator(true);
        player.teleport(plugin.getMap().getLobbySpawn());
        // TODO: 6/17/2021 check if doesnt have a bed

        this.respawnTimer = new AtomicInteger(5);
        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            if (respawnTimer.get() == 0) {
                bukkitTask.cancel();
                Bukkit.getPluginManager().callEvent(new BWPlayerRespawnEvent(event.getBPlayer()));
            } else {
                event.getBPlayer().getPlayer().showTitle(Title.title(
                        Component.text("YOU DIED!", NamedTextColor.RED),
                        Component.text(Utils.color("&eYou will respawn in &c" + respawnTimer.get() + " &eseconds!")),
                        Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
                player.sendMessage(Component.text(Utils.color("&eYou will respawn in &c" + respawnTimer.getAndDecrement() + " &eseconds!")));
            }
        }, 0, 20);
    }
}
