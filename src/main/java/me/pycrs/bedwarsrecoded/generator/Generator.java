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
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                generateResource();
            }
        };
        this.location = location;
        this.item = item;
    }

    /**
     * Location where the items will spawn, i.e. one block above the actual generator's location
     * @return Location
     */
    public Location getResourceLocation() {
        return location.clone().add(.5, 2, .5);
    }

    public Material getItem() {
        return item;
    }

    protected abstract void generateResource();

    public void activate(long period) {
        runnable.runTaskTimer(BedWars.getInstance(), 0, period);
    }

    public void deactivate() {
        runnable.cancel();
    }
}
