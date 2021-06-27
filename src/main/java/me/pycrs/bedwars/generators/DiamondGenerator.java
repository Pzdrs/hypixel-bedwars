package me.pycrs.bedwars.generators;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DiamondGenerator extends Generator {
    private int current = 0, cap;
    public DiamondGenerator(Location location, int cap) {
        super(location);
        this.cap = cap;
    }

    @Override
    protected void generateResource() {
        System.out.println(current + "/" + cap);
        if (current >= cap) return;
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getResource()));
        item.setVelocity(new Vector());
        current++;
    }

    @Override
    protected Material getResource() {
        return Material.DIAMOND;
    }
}
