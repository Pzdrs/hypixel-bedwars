package me.pycrs.bedwars.generators;

import org.bukkit.Location;
import org.bukkit.Material;

public class EmeraldGenerator extends Generator {
    private int cap;

    public EmeraldGenerator(Location location, int cap) {
        super(location);
        this.cap = cap;
    }

    @Override
    protected Material getResource() {
        return Material.EMERALD;
    }
}
