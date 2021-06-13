package me.pycrs.bedwarsrecoded.tasks;

import me.pycrs.bedwarsrecoded.BTeam;
import me.pycrs.bedwarsrecoded.Bedwars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.Bed;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class LobbyLoop extends BukkitRunnable {
    private Bedwars plugin;
    public static AtomicInteger timer;

    public LobbyLoop(Bedwars plugin, int countFrom) {
        this.plugin = plugin;
        timer = new AtomicInteger(countFrom);
    }

    @Override
    public void run() {
        if (timer.get() == 20) {
            broadcastCountdown(timer.get(), NamedTextColor.YELLOW);
        } else if (timer.get() == 10) {
            broadcastCountdown(timer.get(), NamedTextColor.GOLD);
        } else if (timer.get() <= 5 && timer.get() > 0) {
            broadcastCountdown(timer.get(), NamedTextColor.RED);
        }
        if (timer.getAndDecrement() == 0) {
            this.cancel();
            BTeam.distributePlayers();
            plugin.startGame();
        }
    }

    private void broadcastCountdown(int timer, NamedTextColor color) {
        // Chat message
        Utils.inGameBroadcast(Component
                .text("The game starts in ", NamedTextColor.YELLOW)
                .append(Component.text(timer + " ", color))
                .append(Component.text(timer <= 1 ? "second!" : "seconds!")));
        // Sounds
        plugin.getServer().playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT, Sound.Source.BLOCK, 1f, 1f));
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

    public static boolean isCountingDown() {
        return Bedwars.getInstance().getLobbyLoop() != null && !Bedwars.getInstance().getLobbyLoop().isCancelled();
    }
}
