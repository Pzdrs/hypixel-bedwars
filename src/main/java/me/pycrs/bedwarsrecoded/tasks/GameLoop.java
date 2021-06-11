package me.pycrs.bedwarsrecoded.tasks;

import me.pycrs.bedwarsrecoded.BedWars;
import org.bukkit.scheduler.BukkitRunnable;

public class GameLoop extends BukkitRunnable {
    private BedWars plugin;
    private int currentTime = 1;
    int diamondII, emeraldII, diamondIII, emeraldIII, bedDestruction, suddenDeath, gameEnd;

    public GameLoop(BedWars plugin) {
        this.plugin = plugin;

        this.diamondII = plugin.getConfig().getInt("events.diamondTierII");
        this.emeraldII = plugin.getConfig().getInt("events.emeraldTierII");
        this.diamondIII = plugin.getConfig().getInt("events.diamondIII");
        this.emeraldIII = plugin.getConfig().getInt("events.emeraldIII");
        this.bedDestruction = plugin.getConfig().getInt("events.bedDestruction");
        this.suddenDeath = plugin.getConfig().getInt("events.suddenDeath");
        this.gameEnd = plugin.getConfig().getInt("events.gameEnd");
    }

    @Override
    public void run() {
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
