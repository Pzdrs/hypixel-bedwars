package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class Generator {
    private BukkitRunnable runnable;
    private Location location;

    public Generator(Location location) {
        this.location = location;
        this.runnable = createRunnable();
    }

    /**
     * Location where the items will spawn, i.e. one block above the actual generator's location
     *
     * @return Location
     */
    public Location getResourceLocation() {
        return location.clone().add(.5, 2.5, .5);
    }

    protected void generateResource() {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getResource()));
        item.setVelocity(new Vector(0, .1, 0));
    }

    protected abstract Material getResource();

    /**
     * Activates a given generator with the provided period of resource spawning.
     * If the generator is already running, it will be reactivated using the new period value
     *
     * @param period How fast should the generator spawn resources
     */
    public void activate(long period) {
        runnable.runTaskTimer(Bedwars.getInstance(), period, period);
    }

    public void deactivate() {
        runnable.cancel();
        this.runnable = createRunnable();
    }

    private BukkitRunnable createRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                generateResource();
            }
        };
    }

    public static int getProperty(String path, boolean isGeneratorSpeed) {
        return (Bedwars.isSoloOrDoubles() ?
                Bedwars.getInstance().getConfig().getInt("generatorSpeeds1&2." + path) :
                Bedwars.getInstance().getConfig().getInt("generatorSpeeds3&4." + path)) * (isGeneratorSpeed ? 20 : 1);
    }
}
