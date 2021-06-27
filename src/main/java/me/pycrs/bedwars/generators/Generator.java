package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.Bedwars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Generator {
    private BukkitRunnable runnable;
    private Location location;
    private Material item;

    public Generator(Location location, Material item) {
        this.runnable = createRunnable();
        this.location = location;
        this.item = item;
    }

    /**
     * Location where the items will spawn, i.e. one block above the actual generator's location
     * @return Location
     */
    public Location getResourceLocation() {
        return location.clone().add(.5, 1, .5);
    }

    public Material getItem() {
        return item;
    }

    protected abstract void generateResource();

    /**
     * Activates a given generator with the provided period of resource spawning.
     * If the generator is already running, it will be reactivated using the new period value
     * @param period How fast should the generator spawn resources
     */
    public void activate(long period) {
        if (runnable != null && !runnable.isCancelled()) {
            runnable.cancel();
            this.runnable = createRunnable();
        }
        runnable.runTaskTimer(Bedwars.getInstance(), period, period);
    }

    private BukkitRunnable createRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                generateResource();
            }
        };
    }

    public static int getProperty(String path) {
        return (Bedwars.isSoloOrDoubles() ?
                Bedwars.getInstance().getConfig().getInt("generatorSpeeds1&2." + path) :
                Bedwars.getInstance().getConfig().getInt("generatorSpeeds3&4." + path)) * 20;
    }
}
