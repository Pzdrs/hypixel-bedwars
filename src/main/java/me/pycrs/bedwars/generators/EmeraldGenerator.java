package me.pycrs.bedwars.generators;

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
    protected void generateResource() {
        if (current >= cap) return;
        super.generateResource();
        current++;
    }

    public void pickupResource(EntityPickupItemEvent event) {
        current = Math.max(current - event.getItem().getItemStack().getAmount(), 0);
        // TODO: 6/27/2021 track how many diamonds/emeralds/iron/gold players collect
    }

    @Override
    protected Material getResource() {
        return Material.EMERALD;
    }
}
