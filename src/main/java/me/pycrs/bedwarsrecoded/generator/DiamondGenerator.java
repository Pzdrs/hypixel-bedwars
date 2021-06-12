package me.pycrs.bedwarsrecoded.generator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DiamondGenerator extends Generator {
    private int cap;
    public DiamondGenerator(Location location, int cap) {
        super(location, Material.DIAMOND);
        this.cap = cap;
    }

    @Override
    protected void generateResource() {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getItem()));
        item.setVelocity(new Vector());
    }
}
