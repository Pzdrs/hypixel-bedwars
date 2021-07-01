package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.entity.BedwarsTeam;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

public class Forge extends Generator {
    private BedwarsTeam team;
    private int currentIron = 0, currentGold = 0, ironCap, goldCap;

    public Forge(Location location, int ironCap, int goldCap) {
        super(location);
        this.ironCap = ironCap;
        this.goldCap = goldCap;
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
