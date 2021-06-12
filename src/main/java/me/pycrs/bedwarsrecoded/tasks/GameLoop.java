package me.pycrs.bedwarsrecoded.tasks;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

public class GameLoop extends BukkitRunnable {
    private BedWars plugin;
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
    }

    @Override
    public void run() {
        // Initial game setup
        if (currentTime == 0) {
            plugin.getPlayers().forEach(player -> {
                player.getPlayer().setGameMode(GameMode.SURVIVAL);
                //player.teleportToBase();
            });
            plugin.getMap().getDiamondGenerators().forEach(generator -> generator.activate(Utils.getGeneratorStats("diamondI")));
            plugin.getMap().getEmeraldGenerators().forEach(generator -> generator.activate(Utils.getGeneratorStats("emeraldI")));
            plugin.getTeams().forEach(team -> {
                team.getIronGenerator().activate(20);
                team.getGoldGenerator().activate(80);
            });
            currentTime++;
            return;
        }

        // Game events
        if (currentTime == diamondII) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Utils.color("&bDiamond Generators &ehave been upgraded to Tier &cII")));
            plugin.getMap().getDiamondGenerators().forEach(generator -> {
                generator.deactivate();
                generator.activate(Utils.getGeneratorStats("diamondII"));
            });
        } else if (currentTime == diamondIII) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Utils.color("&bDiamond Generators &ehave been upgraded to Tier &cIII")));
            plugin.getMap().getDiamondGenerators().forEach(generator -> {
                generator.deactivate();
                generator.activate(Utils.getGeneratorStats("diamondIII"));
            });
        } else if (currentTime == emeraldII) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Utils.color("&2Emerald Generators &ehave been upgraded to Tier &cII")));
            plugin.getMap().getEmeraldGenerators().forEach(generator -> {
                generator.deactivate();
                generator.activate(Utils.getGeneratorStats("emeraldII"));
            });
        } else if (currentTime == emeraldIII) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Utils.color("&2Emerald Generators &ehave been upgraded to Tier &cIII")));
            plugin.getMap().getEmeraldGenerators().forEach(generator -> {
                generator.deactivate();
                generator.activate(Utils.getGeneratorStats("emeraldIII"));
            });
        } else if (currentTime == bedDestruction) System.out.println("bed destruction");
        else if (currentTime == suddenDeath) System.out.println("dragons spawned");
        else if (currentTime == gameEnd) {
            cancel();
            System.out.println("game ended");
        }
        currentTime++;
    }
}
