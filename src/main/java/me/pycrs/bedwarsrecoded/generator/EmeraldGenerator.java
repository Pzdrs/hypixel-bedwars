package me.pycrs.bedwarsrecoded.generator;

import me.pycrs.bedwarsrecoded.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EmeraldGenerator extends Generator {
    private int cap;

    public EmeraldGenerator(Location location, Material item) {
        super(location, item);
        // FIXME: 6/11/2021 these values are likely wrong and also implement caps
        this.cap = Utils.isSoloOrDoubles() ? 2 : 4;
    }

    @Override
    protected void generateResource() {
        Item item = Bukkit.getWorld("world").dropItem(getResourceLocation(), new ItemStack(getItem()));
        item.setVelocity(new Vector());
    }
}
