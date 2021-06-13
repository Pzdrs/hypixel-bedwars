package me.pycrs.bedwarsrecoded.generators;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class IronGenerator extends Generator implements Splittable {
    public IronGenerator(Location location) {
        super(location, Material.IRON_INGOT);
    }

    @Override
    protected void generateResource() {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getItem()));
        item.setVelocity(new Vector());
    }
}
