package me.pycrs.bedwarsrecoded.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class DiamondGenerator extends Generator{
    public DiamondGenerator(Location location, Material item) {
        super(location, item);
    }

    @Override
    protected BukkitRunnable getRunnable() {
        return null;
    }
}
