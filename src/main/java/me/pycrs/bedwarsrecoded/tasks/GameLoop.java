package me.pycrs.bedwarsrecoded.tasks;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

public class GameLoop extends BukkitRunnable {
    private BedWars plugin;
    private int currentTime = 0;
    int diamondII, emeraldII, diamondIII, emeraldIII, bedDestruction, suddenDeath, gameEnd;
    int diamondGenBaseSpeed;
    int emeraldGenBaseSpeed;

    public GameLoop(BedWars plugin) {
        this.plugin = plugin;

        this.diamondII = plugin.getConfig().getInt("events.diamondII");
        this.emeraldII = plugin.getConfig().getInt("events.emeraldII");
        this.diamondIII = plugin.getConfig().getInt("events.diamondIII");
        this.emeraldIII = plugin.getConfig().getInt("events.emeraldIII");
        this.bedDestruction = plugin.getConfig().getInt("events.bedDestruction");
        this.suddenDeath = plugin.getConfig().getInt("events.suddenDeath");
        this.gameEnd = plugin.getConfig().getInt("events.gameEnd");

        this.diamondGenBaseSpeed = (Utils.isSoloOrDoubles() ?
                plugin.getConfig().getInt("generatorSpeeds1&2.diamond") : plugin.getConfig().getInt("generatorSpeeds3&4.diamond")) * 20;
        this.emeraldGenBaseSpeed = (Utils.isSoloOrDoubles() ?
                plugin.getConfig().getInt("generatorSpeeds1&2.emerald") : plugin.getConfig().getInt("generatorSpeeds3&4.emerald")) * 20;
    }

    @Override
    public void run() {
        // Initial game setup
        if (currentTime == 0) {
            plugin.getPlayers().forEach(player -> {
                player.getPlayer().setGameMode(GameMode.SURVIVAL);
                player.teleportToBase();
            });
            plugin.getMap().getDiamondGenerators().forEach(generator -> generator.activate(diamondGenBaseSpeed));
            plugin.getMap().getEmeraldGenerators().forEach(generator -> generator.activate(emeraldGenBaseSpeed));
            currentTime++;
            return;
        }

        // Game events
        if (currentTime == diamondII) System.out.println("diamond II upgrade");
        else if (currentTime == diamondIII) System.out.println("diamond III upgrade");
        else if (currentTime == emeraldII) System.out.println("emerald II upgrade");
        else if (currentTime == emeraldIII) System.out.println("emerald III upgrade");
        else if (currentTime == bedDestruction) System.out.println("bed destruction");
        else if (currentTime == suddenDeath) System.out.println("dragons spawned");
        else if (currentTime == gameEnd) {
            cancel();
            System.out.println("game ended");
        }
        currentTime++;
    }
}
