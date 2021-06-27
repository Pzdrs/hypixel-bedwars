package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.BedwarsTeam;
import org.bukkit.Location;
import org.bukkit.Material;

public class Forge extends Generator {
    private BedwarsTeam team;
    public Forge(Location location) {
        super(location);
    }

    @Override
    protected Material getResource() {
        // TODO: 6/27/2021 come up with some sort of a formula for figuring out what item to spawn (iron, gold, emerald)
        return Material.IRON_INGOT;
    }

    public void setTeam(BedwarsTeam team) {
        this.team = team;
    }
}
