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
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerJoinListener implements Listener {
    private BedWars bedWars;
    public BukkitTask countDown;

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
        if (Bukkit.getOnlinePlayers().size() >= bedWars.getMode().getMinPlayers()) {
            AtomicInteger timer = new AtomicInteger(bedWars.getConfig().getInt("lobbyCountdown"));
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
                    BedWars.setGameInProgress(true);
                }
            }, 0, 20);
        }
    }

    private void broadcastCountdown(int timer, NamedTextColor color) {
        Utils.inGameBroadcast(Component
                .text("The game starts in ", NamedTextColor.YELLOW)
                .append(Component.text(timer + " ", color))
                .append(Component.text(timer <= 1 ? "second!" : "seconds!")));
    }
}
