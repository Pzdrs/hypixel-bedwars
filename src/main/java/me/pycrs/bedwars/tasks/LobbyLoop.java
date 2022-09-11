package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.events.BedwarsGameStartEvent;
import me.pycrs.bedwars.scoreboard.LobbyScoreboard;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class LobbyLoop extends BedwarsRunnable {
    private static LobbyLoop INSTANCE;

    public static void start(Bedwars plugin) {
        if (INSTANCE == null) INSTANCE = new LobbyLoop(plugin, Settings.lobbyCountdown);
        INSTANCE.runTaskTimer(Bedwars.getInstance(), 0, 20);
        Bedwars.setGameStage(Bedwars.GameStage.LOBBY_COUNTDOWN);
        LobbyScoreboard.get().getBody().updateLine("countdown");
    }

    public static void stop() {
        INSTANCE.cancel();
        INSTANCE = null;
        LobbyScoreboard.get().getBody().updateLine("countdown");
    }

    public static AtomicInteger timer;

    public LobbyLoop(Bedwars plugin, int countFrom) {
        super(plugin);
        timer = new AtomicInteger(countFrom);
    }

    @Override
    public void run() {
        LobbyScoreboard.get().getBody().updateLine("countdown");
        if (timer.get() == 20) {
            broadcastCountdown(timer.get(), NamedTextColor.YELLOW);
        } else if (timer.get() == 10) {
            broadcastCountdown(timer.get(), NamedTextColor.GOLD);
        } else if (timer.get() <= 5 && timer.get() > 0) {
            broadcastCountdown(timer.get(), NamedTextColor.RED);
        }
        if (timer.getAndDecrement() == 0) {
            this.cancel();
            Bukkit.getPluginManager().callEvent(new BedwarsGameStartEvent(Bedwars.getInstance()));
        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        Bedwars.setGameStage(Bedwars.GameStage.LOBBY_WAITING);
        LobbyScoreboard.get().getBody().updateLine("countdown");
    }

    private void broadcastCountdown(int timer, NamedTextColor color) {
        // Chat message
        Utils.inGameBroadcast(Component
                .text("The game starts in ", NamedTextColor.YELLOW)
                .append(Component.text(timer + " ", color))
                .append(Component.text(timer <= 1 ? "second!" : "seconds!")));
        // Sounds
        Bukkit.getServer().playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT, Sound.Source.BLOCK, 1f, 1f));
        // Titles
        switch (timer) {
            case 10 ->
                    Bukkit.getServer().showTitle(Title.title(Component.text(timer, NamedTextColor.GREEN), Component.text().asComponent(),
                            Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
            case 5, 4 ->
                    Bukkit.getServer().showTitle(Title.title(Component.text(timer, NamedTextColor.YELLOW), Component.text().asComponent(),
                            Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
            case 3, 2, 1 ->
                    Bukkit.getServer().showTitle(Title.title(Component.text(timer, NamedTextColor.RED), Component.text().asComponent(),
                            Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
        }
    }
}
