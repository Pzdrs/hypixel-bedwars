package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.gameevent.GameEvent;
import me.pycrs.bedwars.generators.Generator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameLoop extends BukkitRunnable {
    private Bedwars plugin;
    private List<GameEvent> events;
    private int currentTime = 0;

    public GameLoop(Bedwars plugin) {
        this.plugin = plugin;
        this.events = new ArrayList<>(Arrays.asList(
                new GameEvent.Builder()
                        .period(0)
                        .broadcast(Component.text("the big ass welcome message im not bothered to make rn"))
                        .handle(() -> {
                            // Initial setup
                            plugin.getPlayers().forEach(player -> {
                                player.getPlayer().setGameMode(GameMode.SURVIVAL);
                                player.teleportToBase();
                            });

                            // Initial generator activation
                            plugin.getMap().getDiamondGenerators().forEach(generator -> generator.activate(Generator.getProperty("diamondI", true)));
                            plugin.getMap().getEmeraldGenerators().forEach(generator -> generator.activate(Generator.getProperty("emeraldI", true)));
                            plugin.getTeams().forEach(team -> {
                                // These will depend on what map is in use
                                team.getIronGenerator().activate(20);
                            });
                        }).build(),
                new GameEvent.Builder()
                        .period(plugin.getConfig().getInt("events.diamondII"))
                        .broadcast(Component.text("Diamond Generators", NamedTextColor.AQUA)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("II", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getDiamondGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("diamondII", true));
                        })).build(),
                new GameEvent.Builder()
                        .period(plugin.getConfig().getInt("events.emeraldII"))
                        .broadcast(Component.text("Emerald Generators", NamedTextColor.DARK_GREEN)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("II", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getEmeraldGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("emeraldII",true));
                        })).build(),
                new GameEvent.Builder()
                        .period(plugin.getConfig().getInt("events.diamondIII"))
                        .broadcast(Component.text("Diamond Generators", NamedTextColor.AQUA)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("III", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getDiamondGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("diamondIII",true));
                        })).build(),
                new GameEvent.Builder()
                        .period(plugin.getConfig().getInt("events.emeraldIII"))
                        .broadcast(Component.text("Emerald Generators", NamedTextColor.DARK_GREEN)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("III", NamedTextColor.RED)))
                        .handle(() -> plugin.getMap().getEmeraldGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("emeraldIII",true));
                        })).build(),
                new GameEvent.Builder()
                        .period(plugin.getConfig().getInt("events.bedDestruction"))
                        .before(new GameEvent.Builder()
                                .period(plugin.getConfig().getInt("events.bedDestruction") - 300)
                                .broadcast(Component.text("All beds will be destroyed in 5 minutes!", NamedTextColor.RED, TextDecoration.BOLD)).build())
                        .handle(() -> {
                            System.out.println("bed destroyed");
                        }).build(),
                new GameEvent.Builder()
                        .period(plugin.getConfig().getInt("events.suddenDeath"))
                        .handle(() -> {
                            System.out.println("dragons spawned");
                        }).build(),
                new GameEvent.Builder()
                        .period(plugin.getConfig().getInt("events.gameEnd"))
                        .handle(() -> {
                            System.out.println("game ended");
                            cancel();
                        }).build()
        ));
    }

    @Override
    public void run() {
        events.forEach(gameEvent -> gameEvent.proceed(currentTime));
        currentTime++;
    }
}