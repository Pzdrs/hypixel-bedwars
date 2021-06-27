package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.BedwarsTeam;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

public class Forge extends Generator {
    private BedwarsTeam team;

    public Forge(Location location) {
        super(location);
    }

    @Override
    public Location getResourceLocation() {
        return location.clone().add(.5, 1, .5);
    }

    @Override
    protected Material getResource() {
        int rnd = new Random().nextInt(100);
        if (rnd < 20) {
            return Material.GOLD_INGOT;
        } else return Material.IRON_INGOT;
    }

    public void setTeam(BedwarsTeam team) {
        this.team = team;
    }
}
