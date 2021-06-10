package me.pycrs.bedwarsrecoded.generator;

import me.pycrs.bedwarsrecoded.BedWars;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Generator {
    private BukkitRunnable runnable;
    private Material item;

    public Generator(Material item) {
        this.item = item;
        this.runnable = getRunnable();
    }

    protected abstract BukkitRunnable getRunnable();

    public void activate(long period) {
        runnable.runTaskTimer(BedWars.getInstance(), 0, period);
    }

    public void deactivate() {
        runnable.cancel();
    }
}
