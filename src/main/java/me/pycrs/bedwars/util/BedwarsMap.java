package me.pycrs.bedwars.util;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Mode;
import me.pycrs.bedwars.generators.*;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.data.type.Bed;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BedwarsMap {
    private final String name;
    private final Mode[] modes;
    private final Location lobbySpawn;
    private final List<Generator> diamondGenerators, emeraldGenerators;

    public BedwarsMap(String name, List<Object> modes, Location lobbySpawn, JSONObject mapData) {
        this.name = name;
        this.lobbySpawn = lobbySpawn;
        this.modes = new Mode[modes.size()];
        for (int i = 0; i < modes.size(); i++) this.modes[i] = Mode.valueOf(String.valueOf(modes.get(i)));
        this.diamondGenerators = new ArrayList<>();
        this.emeraldGenerators = new ArrayList<>();

        // Sanitize the physical world
        World world = Bukkit.getWorld("world");
        if (world == null) return;

        // Removes all potential items, mobs, etc., while leaving item frames and such intact
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Item || entity instanceof Mob) entity.remove();
        }

        mapData.getJSONArray("teams").forEach(teamData -> {
            JSONObject shops = ((JSONObject) teamData).getJSONObject("shops");

            Location itemShop = new Location(
                    Bukkit.getWorld("world"),
                    shops.getJSONObject("item").getDouble("x"),
                    shops.getJSONObject("item").getDouble("y"),
                    shops.getJSONObject("item").getDouble("z")
            );
            Location teamUpgradesShop = new Location(
                    Bukkit.getWorld("world"),
                    shops.getJSONObject("teamUpgrades").getDouble("x"),
                    shops.getJSONObject("teamUpgrades").getDouble("y"),
                    shops.getJSONObject("teamUpgrades").getDouble("z")
            );

            world.spawnEntity(itemShop, EntityType.VILLAGER);
            world.spawnEntity(teamUpgradesShop, EntityType.VILLAGER);
        });
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

    public static Optional<JSONObject> loadJSON() {
        try {
            return Optional.of(new JSONObject(Files.readString(Paths.get("world/map.json"))));
        } catch (IOException e) {
            Bedwars.getInstance().getLogger().severe("Couldn't load world/map.json, it's either corrupted or doesn't exist");
        }
        return Optional.empty();
    }

    public static BedwarsMap createMap(JSONObject mapData) {
        JSONObject lobbySpawn = mapData.getJSONObject("lobbySpawn");
        BedwarsMap bedwarsMap = new BedwarsMap(mapData.getString("name"), mapData.getJSONArray("mode").toList(),
                new Location(
                        Bukkit.getWorld("world"),
                        lobbySpawn.getDouble("x"),
                        lobbySpawn.getDouble("y"),
                        lobbySpawn.getDouble("z"),
                        lobbySpawn.getFloat("yaw"),
                        lobbySpawn.getFloat("pitch")), mapData);

        JSONArray diamondsGens = mapData.getJSONArray("diamondGenerators");
        JSONArray emeraldGens = mapData.getJSONArray("emeraldGenerators");

        diamondsGens.forEach(object -> BedwarsMap.addDiamondGenerator(object, bedwarsMap));
        emeraldGens.forEach(object -> BedwarsMap.addEmeraldGenerator(object, bedwarsMap));

        return bedwarsMap;
    }

    private static void addDiamondGenerator(Object object, BedwarsMap map) {
        JSONObject gen = new JSONObject(object.toString());
        map.getDiamondGenerators().add(new DiamondGenerator(new Location(
                Bukkit.getWorld("world"),
                gen.getDouble("x"),
                gen.getDouble("y"),
                gen.getDouble("z")
        ), Generator.getProperty("diamondCap", false)));
    }

    private static void addEmeraldGenerator(Object object, BedwarsMap map) {
        JSONObject gen = new JSONObject(object.toString());
        map.getEmeraldGenerators().add(new EmeraldGenerator(new Location(
                Bukkit.getWorld("world"),
                gen.getDouble("x"),
                gen.getDouble("y"),
                gen.getDouble("z")
        ), Generator.getProperty("emeraldCap", false)));
    }
}
