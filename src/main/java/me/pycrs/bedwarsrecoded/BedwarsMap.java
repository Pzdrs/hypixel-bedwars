package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.generators.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BedwarsMap {
    private String name;
    private Mode[] modes;
    private Location lobbySpawn;
    private List<Generator> diamondGenerators, emeraldGenerators;

    public BedwarsMap(String name, List<Object> modes, Location lobbySpawn) {
        this.name = name;
        this.lobbySpawn = lobbySpawn;
        this.modes = new Mode[modes.size()];
        for (int i = 0; i < modes.size(); i++) this.modes[i] = Mode.valueOf(String.valueOf(modes.get(i)));
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

    public Location getLobbySpawnExact() {
        return lobbySpawn.clone().add(.5, 1, .5);
    }

    public List<Generator> getDiamondGenerators() {
        return diamondGenerators;
    }

    public List<Generator> getEmeraldGenerators() {
        return emeraldGenerators;
    }

    @Override
    public String toString() {
        return "BedwarsMap{" +
                "name='" + name + '\'' +
                ", modes=" + Arrays.toString(modes) +
                ", lobbySpawn=" + lobbySpawn +
                ", diamondGenerators=" + diamondGenerators +
                ", emeraldGenerators=" + emeraldGenerators +
                '}';
    }

    public static BedwarsMap createMap(JSONObject map) {
        JSONObject lobbySpawn = map.getJSONObject("lobbySpawn");
        return new BedwarsMap(map.getString("name"), map.getJSONArray("mode").toList(),
                new Location(
                        Bukkit.getWorld("world"),
                        lobbySpawn.getDouble("x"),
                        lobbySpawn.getDouble("y"),
                        lobbySpawn.getDouble("z"),
                        lobbySpawn.getFloat("yaw"),
                        lobbySpawn.getFloat("pitch")));
    }

    public static void addDiamondGenerator(Object object, BedwarsMap map) {
        JSONObject gen = new JSONObject(object.toString());
        map.getDiamondGenerators().add(new DiamondGenerator(new Location(
                Bukkit.getWorld("world"),
                gen.getDouble("x"),
                gen.getDouble("y"),
                gen.getDouble("z")
        ), Utils.getGeneratorStats("diamondCap")));
    }

    public static void addEmeraldGenerator(Object object, BedwarsMap map) {
        JSONObject gen = new JSONObject(object.toString());
        map.getEmeraldGenerators().add(new EmeraldGenerator(new Location(
                Bukkit.getWorld("world"),
                gen.getDouble("x"),
                gen.getDouble("y"),
                gen.getDouble("z")
        ), Utils.getGeneratorStats("emeraldCap")));
    }
}
