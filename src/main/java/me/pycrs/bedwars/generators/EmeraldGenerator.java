package me.pycrs.bedwars.generators;

import org.bukkit.Location;
import org.bukkit.Material;

public class EmeraldGenerator extends Generator {
    private int current = 0, cap;

    public EmeraldGenerator(Location location, int cap) {
        super(location);
        this.cap = cap;
    }

    @Override
    protected void generateResource() {
        if (current >= cap) return;
        super.generateResource();
        current++;
    }

    @Override
    protected Material getResource() {
        return Material.EMERALD;
    }
}
