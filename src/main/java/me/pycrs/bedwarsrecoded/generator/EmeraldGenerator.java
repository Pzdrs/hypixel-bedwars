package me.pycrs.bedwarsrecoded.generator;

import me.pycrs.bedwarsrecoded.BedwarsMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EmeraldGenerator extends Generator{
    public EmeraldGenerator(BedwarsMap map, Location location, Material item) {
        super(map, location, item);
    }

    @Override
    protected void generateResource(BedwarsMap map) {
        map.getEmeraldGenerators().forEach(generator -> {
            Item item = Bukkit.getWorld("world").dropItem(generator.getResourceLocation(), new ItemStack(generator.getItem()));
            item.setVelocity(new Vector());
        });
    }
}
