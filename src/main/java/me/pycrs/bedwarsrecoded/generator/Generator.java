package me.pycrs.bedwarsrecoded.generator;

import me.pycrs.bedwarsrecoded.BedWars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Generator {
    private BukkitRunnable runnable;
    private Location location;
    private Material item;

    public Generator(Location location, Material item) {
        this.runnable = getRunnable();
        this.location = location;
        this.item = item;
    }

    protected abstract BukkitRunnable getRunnable();

    public void activate(long period) {
        runnable.runTaskTimer(BedWars.getInstance(), 0, period);
    }

    public void deactivate() {
        runnable.cancel();
    }
}
