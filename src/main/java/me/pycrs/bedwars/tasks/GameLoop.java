package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.team.BedwarsTeamList;
import me.pycrs.bedwars.util.GameEvent;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsGameEndEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class GameLoop extends BedwarsRunnable {
    private static GameLoop INSTANCE;

    public static void start(Bedwars plugin) {
        INSTANCE = new GameLoop(plugin);
        INSTANCE.runTaskTimer(plugin, 0, 20);
    }

    public static void stop() {
        INSTANCE.cancel();
    }
    private final List<GameEvent> events;
    private int currentTime = 0;

    public GameLoop(Bedwars plugin) {
        super(plugin);
        this.events = new ArrayList<>(Arrays.asList(
                new GameEvent.Builder()
                        .period(Settings.eventDiamondII)
                        .broadcast(Component.text("Diamond Generators", NamedTextColor.AQUA)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("II", NamedTextColor.RED)))
                        .handle(() -> Bedwars.getMap().getDiamondGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("diamondII", true));
                        })).build(),
                new GameEvent.Builder()
                        .period(Settings.eventEmeraldII)
                        .broadcast(Component.text("Emerald Generators", NamedTextColor.DARK_GREEN)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("II", NamedTextColor.RED)))
                        .handle(() -> Bedwars.getMap().getEmeraldGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("emeraldII", true));
                        })).build(),
                new GameEvent.Builder()
                        .period(Settings.eventDiamondIII)
                        .broadcast(Component.text("Diamond Generators", NamedTextColor.AQUA)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("III", NamedTextColor.RED)))
                        .handle(() -> Bedwars.getMap().getDiamondGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("diamondIII", true));
                        })).build(),
                new GameEvent.Builder()
                        .period(Settings.eventEmeraldIII)
                        .broadcast(Component.text("Emerald Generators", NamedTextColor.DARK_GREEN)
                                .append(Component.text(" have been upgraded to Tier ", NamedTextColor.YELLOW))
                                .append(Component.text("III", NamedTextColor.RED)))
                        .handle(() -> Bedwars.getMap().getEmeraldGenerators().forEach(generator -> {
                            generator.deactivate();
                            generator.activate(Generator.getProperty("emeraldIII", true));
                        })).build(),
                new GameEvent.Builder()
                        .period(Settings.eventBedDestruction)
                        .before(new GameEvent.Builder()
                                .period(Settings.eventBedDestruction - 300)
                                .broadcast(Component.text("All beds will be destroyed in 5 minutes!", NamedTextColor.RED, TextDecoration.BOLD)).build())
                        .handle(() -> BedwarsTeamList.getList().forEach(BedwarsTeam::destroyBed)).build(),
                new GameEvent.Builder()
                        .period(Settings.eventSuddenDeath)
                        .handle(() -> {
                            System.out.println("dragons spawned");
                        }).build(),
                new GameEvent.Builder()
                        .period(Settings.eventGameEnd)
                        .handle(() -> {
                            // TODO: 7/20/2021 it's not suppose to be random i think there is some sort of formula for determining the winning team but i cannot find it anywhere
                            Bukkit.getServer().getPluginManager().callEvent(new BedwarsGameEndEvent(plugin, BedwarsGameEndEvent.Result.GAME_END,
                                    BedwarsTeamList.getList().get(new Random().nextInt(BedwarsTeamList.getList().size()))));
                            cancel();
                        }).build()
        ));
    }

    @Override
    public void run() {
        int lastReward = -20;
        if (currentTime == lastReward + 60) {
            Utils.inGameBroadcast(Component.text("+25 Bed Wars Experience (Time Played)", NamedTextColor.AQUA));
            Utils.inGameBroadcast(Component.text("+12 coins! (Time Played)", NamedTextColor.GOLD));
            lastReward = currentTime;
        }
        events.forEach(gameEvent -> gameEvent.proceed(currentTime));
        currentTime++;
    }
}