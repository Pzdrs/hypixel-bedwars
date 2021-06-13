package me.pycrs.bedwarsrecoded.generators;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EmeraldGenerator extends Generator {
    private int cap;

    public EmeraldGenerator(Location location, int cap) {
        super(location, Material.EMERALD);
        this.cap = cap;
    }

    @Override
    protected void generateResource() {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getItem()));
        item.setVelocity(new Vector());
    }
}
