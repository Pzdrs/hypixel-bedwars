package me.pycrs.bedwars.generators;

import org.bukkit.Location;
import org.bukkit.Material;

public class IronGenerator extends Generator implements Splittable {
    public IronGenerator(Location location) {
        super(location);
    }

    @Override
    protected Material getResource() {
        return Material.IRON_INGOT;
    }
}
