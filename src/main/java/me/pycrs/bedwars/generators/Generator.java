package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.util.BedwarsMap;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class Generator {
    /**
     * Defines generator resource states, an item is marked as {@link ResourceState#FRESH_SPAWN} when it's spawned, then when the item
     * is picked up by a player, it is marked as {@link ResourceState#PICKED_UP}
     */
    public enum ResourceState {
        FRESH_SPAWN(0), PICKED_UP(1);

        private final int state;

        ResourceState(int state) {
            this.state = state;
        }

        public int state() {
            return state;
        }
    }

    /**
     * Defines the marker key for generated items (distinguishing between picked up and not yet picked up items)
     */
    public static final NamespacedKey RESOURCE_MARKER_KEY = new NamespacedKey(Bedwars.getInstance(), "resource_state");

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

    protected void generateResource(Material material) {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(material));
        item.setVelocity(new Vector(0, .1, 0));
    }

    protected void generateResource(ItemStack itemStack) {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), itemStack);
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

    public Location getLocation() {
        return location;
    }

    private BukkitRunnable createRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                generateResource(getResource());
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
            case IRON_INGOT:
            case GOLD_INGOT:
                Bedwars.getInstance().getTeams().forEach(team -> {
                    Location item = event.getItem().getLocation();
                    item.setY(team.getForge().location.getY());
                    if (team.getForge().location.distance(item) < 1) {
                        team.getForge().pickupResource(event, bedwarsPlayer, event.getItem().getItemStack().getType());
                    }
                });
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
