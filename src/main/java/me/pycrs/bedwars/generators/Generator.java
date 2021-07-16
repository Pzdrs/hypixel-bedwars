package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.BedwarsMap;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class Generator {
    private BukkitRunnable runnable;
    protected Location location;

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
        return (Settings.isSoloOrDoubles() ?
                Bedwars.getInstance().getConfig().getInt("generators1&2." + path) :
                Bedwars.getInstance().getConfig().getInt("generators3&4." + path)) * (isGeneratorSpeed ? 20 : 1);
    }

    public static boolean pickupCheck(BedwarsMap map, EntityPickupItemEvent event) {
        BedwarsPlayer bedwarsPlayer = BedwarsPlayer.toBPlayer((Player) event.getEntity());
        switch (event.getItem().getItemStack().getType()) {
            case DIAMOND:
                for (Generator diamondGenerator : map.getDiamondGenerators()) {
                    Location item = event.getItem().getLocation();
                    item.setY(diamondGenerator.location.getY());
                    if (diamondGenerator.location.distance(item) < 1) {
                        ((DiamondGenerator) diamondGenerator).pickupResource(event, bedwarsPlayer);
                        return true;
                    }
                }
            case EMERALD:
                for (Generator emeraldGenerator : map.getEmeraldGenerators()) {
                    Location item = event.getItem().getLocation();
                    item.setY(emeraldGenerator.location.getY());
                    if (emeraldGenerator.location.distance(item) < 1) {
                        ((EmeraldGenerator) emeraldGenerator).pickupResource(event, bedwarsPlayer);
                        return true;
                    }
                }
        }
        return false;
    }
}
