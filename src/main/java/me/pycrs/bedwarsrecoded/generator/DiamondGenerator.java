package me.pycrs.bedwarsrecoded.generator;

import me.pycrs.bedwarsrecoded.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DiamondGenerator extends Generator {
    public DiamondGenerator(Location location, Material item) {
        super(location, item);
    }

    @Override
    protected void generateResource() {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getItem()));
        item.setVelocity(new Vector());
    }
}
