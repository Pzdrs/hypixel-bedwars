package me.pycrs.bedwarsrecoded.generator;

import me.pycrs.bedwarsrecoded.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DiamondGenerator extends Generator {
    private int cap;
    public DiamondGenerator(Location location, Material item) {
        super(location, item);
        // TODO: 6/11/2021 implement caps
        this.cap = Utils.isSoloOrDoubles() ? 4 : 8;
    }

    @Override
    protected void generateResource() {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getItem()));
        item.setVelocity(new Vector());
    }
}
