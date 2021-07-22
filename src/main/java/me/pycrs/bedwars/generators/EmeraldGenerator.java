package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EmeraldGenerator extends Generator {
    private int current = 0, cap;

    public EmeraldGenerator(Location location, int cap) {
        super(location);
        this.cap = cap;
    }

    @Override
    protected void generateResource(Material material) {
        if (current >= cap) return;
        super.generateResource(material);
        current++;
    }

    protected void pickupResource(EntityPickupItemEvent event, BedwarsPlayer bedwarsPlayer) {
        current = Math.max(current - event.getItem().getItemStack().getAmount(), 0);
        bedwarsPlayer.getStatistics().addResources(event.getItem().getItemStack());
    }

    @Override
    protected Material getResource() {
        return Material.EMERALD;
    }
}
