package me.pycrs.bedwarsrecoded.tasks;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.GameEvent;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLoop extends BukkitRunnable {
    private BedWars plugin;
    private List<GameEvent> events;
    private int currentTime = 0;
    int diamondII, emeraldII, diamondIII, emeraldIII, bedDestruction, suddenDeath, gameEnd;

    public GameLoop(BedWars plugin) {
        this.plugin = plugin;

        this.diamondII = plugin.getConfig().getInt("events.diamondII");
        this.emeraldII = plugin.getConfig().getInt("events.emeraldII");
        this.diamondIII = plugin.getConfig().getInt("events.diamondIII");
        this.emeraldIII = plugin.getConfig().getInt("events.emeraldIII");
        this.bedDestruction = plugin.getConfig().getInt("events.bedDestruction");
        this.suddenDeath = plugin.getConfig().getInt("events.suddenDeath");
        this.gameEnd = plugin.getConfig().getInt("events.gameEnd");

        this.events = new ArrayList<>(Arrays.asList(
                new GameEvent.Builder()
                        .period(0)
                        .handle(() -> {
                            plugin.getPlayers().forEach(player -> {
                                // TODO: 6/12/2021 print the big ass welcome message
                                player.getPlayer().setGameMode(GameMode.SURVIVAL);
                                player.teleportToBase();
                            });
                            plugin.getMap().getDiamondGenerators().forEach(generator -> generator.activate(Utils.getGeneratorStats("diamondI")));
                            plugin.getMap().getEmeraldGenerators().forEach(generator -> generator.activate(Utils.getGeneratorStats("emeraldI")));
                            plugin.getTeams().forEach(team -> {
                                team.getIronGenerator().activate(20);
                                team.getGoldGenerator().activate(80);
                            });
                        }).build(),
                new GameEvent.Builder()
                        .period(diamondII)
                        .broadcast(Component.text("Diamond Generators", NamedTextColor.AQUA)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("II", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getDiamondGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Utils.getGeneratorStats("diamondII"));
                        })).build(),
                new GameEvent.Builder()
                        .period(emeraldII)
                        .broadcast(Component.text("Emerald Generators", NamedTextColor.DARK_GREEN)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("II", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getEmeraldGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Utils.getGeneratorStats("emeraldII"));
                        })).build(),
                new GameEvent.Builder()
                        .period(diamondIII)
                        .broadcast(Component.text("Diamond Generators", NamedTextColor.AQUA)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("III", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getDiamondGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Utils.getGeneratorStats("diamondIII"));
                        })).build(),
                new GameEvent.Builder()
                        .period(emeraldIII)
                        .broadcast(Component.text("Emerald Generators", NamedTextColor.DARK_GREEN)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("III", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getEmeraldGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Utils.getGeneratorStats("emeraldIII"));
                        })).build(),
                new GameEvent.Builder()
                        .period(bedDestruction)
                        .before(new GameEvent.Builder()
                                .period(bedDestruction - 300)
                                .broadcast(Component.text("All beds will be destroyed in 5 minutes!", NamedTextColor.RED, TextDecoration.BOLD)).build())
                        .handle(() -> {
                            System.out.println("bed destroyed");
                        }).build(),
                new GameEvent.Builder()
                        .period(suddenDeath)
                        .handle(() -> {
                            System.out.println("dragons spawned");
                        }).build(),
                new GameEvent.Builder()
                        .period(gameEnd)
                        .handle(() -> {
                            System.out.println("game ended");
                            cancel();
                        }).build()
        ));
    }

    @Override
    public void run() {
        System.out.println(currentTime);
        for (GameEvent event : events) event.proceed(currentTime);
        currentTime++;
    }
}