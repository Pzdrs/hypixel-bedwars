package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Utils;
import me.pycrs.bedwars.events.BedwarsPlayerRespawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class BedwarsPlayerRespawnListener implements Listener {
    private Bedwars plugin;
    private AtomicInteger respawnTimer;

    public BedwarsPlayerRespawnListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // FIXME: 6/20/2021 if multiple people respawning at one time they all share respawnTimer variable oof
    @EventHandler
    public void onPlayerRespawn(BedwarsPlayerRespawnEvent event) {
        Player player = event.getBPlayer().getPlayer();
        this.respawnTimer = new AtomicInteger(5);

        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            System.out.println(respawnTimer);
            if (respawnTimer.get() == 0) {
                bukkitTask.cancel();
                event.getBPlayer().setSpectator(false);
                player.setInvisible(false);
                player.sendMessage(Component.text("You have respawned!", NamedTextColor.YELLOW));
                player.showTitle(Title.title(Component.text("RESPAWNED!", NamedTextColor.GREEN), Component.empty(),
                        Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
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
