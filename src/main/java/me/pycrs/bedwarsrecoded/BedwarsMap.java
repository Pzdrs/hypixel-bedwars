package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.generator.Generator;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class BedwarsMap {
    private String name;
    private Mode[] modes;
    private Location lobbySpawn;
    private List<Generator> diamondGenerators, emeraldGenerators;

    public BedwarsMap(String name, Mode[] modes, Location lobbySpawn) {
        this.name = name;
        this.modes = modes;
        this.lobbySpawn = lobbySpawn;
        this.diamondGenerators = new ArrayList<>();
        this.emeraldGenerators = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Mode[] getModes() {
        return modes;
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public List<Generator> getDiamondGenerators() {
        return diamondGenerators;
    }

    public List<Generator> getEmeraldGenerators() {
        return emeraldGenerators;
    }
}
