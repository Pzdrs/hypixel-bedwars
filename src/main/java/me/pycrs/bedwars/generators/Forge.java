package me.pycrs.bedwars.generators;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class Forge extends Generator {
    private BedwarsTeam team;
    private int currentIron = 0, currentGold = 0, ironCap, goldCap;

    public Forge(Location location, int ironCap, int goldCap) {
        super(location);
        this.ironCap = ironCap;
        this.goldCap = goldCap;
    }

    @Override
    public Location getResourceLocation() {
        return location.clone().add(.5, 1, .5);
    }

    @Override
    protected void generateResource(Material material) {
        if (currentIron >= ironCap || currentGold >= goldCap) return;
        ItemStack resource = new ItemBuilder(material)
                .setPersistentData("resourceState", PersistentDataType.INTEGER, 0)
                .build();
        super.generateResource(resource);
        if (material == Material.IRON_INGOT) currentIron++;
        else if (material == Material.GOLD_INGOT) currentGold++;
    }

    @Override
    protected Material getResource() {
        int rnd = new Random().nextInt(100);
        if (rnd < 20) {
            return Material.GOLD_INGOT;
        } else return Material.IRON_INGOT;
    }

    protected void pickupResource(EntityPickupItemEvent event, BedwarsPlayer bedwarsPlayer, Material material) {
        if (material == Material.IRON_INGOT)
            currentIron = Math.max(currentIron - event.getItem().getItemStack().getAmount(), 0);
        else if (material == Material.GOLD_INGOT)
            currentGold = Math.max(currentGold - event.getItem().getItemStack().getAmount(), 0);
        bedwarsPlayer.getStatistics().addResources(event.getItem().getItemStack());
    }

    public void setTeam(BedwarsTeam team) {
        this.team = team;
    }
}
