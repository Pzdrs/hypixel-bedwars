package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerJoinListener implements Listener {
    private BedWars bedWars;
    public BukkitTask countDown;
    private AtomicInteger timer;

    public PlayerJoinListener(BedWars bedWars) {
        this.bedWars = bedWars;
        bedWars.getServer().getPluginManager().registerEvents(this, bedWars);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Get rid of the default join message
        event.joinMessage(null);

        Player player = event.getPlayer();

        // Set player's display name
        player.displayName(Component.text(event.getPlayer().getName(), NamedTextColor.GRAY));

        // New join messages
        Bukkit.getServer().sendMessage(player.displayName()
                .append(Component.text(" has joined ", NamedTextColor.YELLOW))
                .append(Component.text(
                        Utils.color("&e(&b" + Bukkit.getOnlinePlayers().size() + "&e/&b" +
                                bedWars.getMode().getTeamSize() * bedWars.getMode().getAmountOfTeams() + "&e)!"))));

        // If enough players, start the countdown
        if (countDown == null || countDown.isCancelled()) {
            if (Bukkit.getOnlinePlayers().size() >= bedWars.getMode().getMinPlayers()) {
                this.timer = new AtomicInteger(bedWars.getConfig().getInt("lobbyCountdown"));
                this.countDown = Bukkit.getScheduler().runTaskTimer(bedWars, () -> {
                    if (timer.get() == 20) {
                        broadcastCountdown(timer.get(), NamedTextColor.YELLOW);
                    } else if (timer.get() == 10) {
                        broadcastCountdown(timer.get(), NamedTextColor.GOLD);
                    } else if (timer.get() <= 5) {
                        broadcastCountdown(timer.get(), NamedTextColor.RED);
                    }
                    if (timer.decrementAndGet() == 0) {
                        countDown.cancel();
                        bedWars.setGameInProgress(true);
                    }
                }, 0, 20);
            }
        } else {
            player.sendMessage(Component.text(Utils.color("&eThe game is starting in&b " + timer + " &e" + (timer.get() <= 1 ? "second!" : "seconds!"))));
        }
    }

    private void broadcastCountdown(int timer, NamedTextColor color) {
        // Chat message
        Utils.inGameBroadcast(Component
                .text("The game starts in ", NamedTextColor.YELLOW)
                .append(Component.text(timer + " ", color))
                .append(Component.text(timer <= 1 ? "second!" : "seconds!")));
        // Sounds
        bedWars.getServer().playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT, Sound.Source.BLOCK, 1f, 1f));
        // Titles
        switch (timer) {
            case 10:
                Bukkit.getServer().showTitle(Title.title(Component.text(timer, NamedTextColor.GREEN), Component.text().asComponent(),
                        Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
                break;
            case 5:
            case 4:
                Bukkit.getServer().showTitle(Title.title(Component.text(timer, NamedTextColor.YELLOW), Component.text().asComponent(),
                        Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
                break;
            case 3:
            case 2:
            case 1:
                Bukkit.getServer().showTitle(Title.title(Component.text(timer, NamedTextColor.RED), Component.text().asComponent(),
                        Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
                break;
        }
    }
}
