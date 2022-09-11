package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class BedwarsRunnable extends BukkitRunnable {
    protected final Bedwars plugin;

    public BedwarsRunnable(Bedwars plugin) {
        this.plugin = plugin;
    }
}
