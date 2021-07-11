package me.pycrs.bedwars.entities.player;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class PlayerStatistics {
    private int kills, finalKills, deaths, beds, ironCollected, goldCollected, emeraldCollected;

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addResources(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case IRON_INGOT:
                ironCollected += itemStack.getAmount();
                break;
            case GOLD_INGOT:
                goldCollected += itemStack.getAmount();
                break;
            case EMERALD:
                emeraldCollected += itemStack.getAmount();
                break;
        }
    }

    public int getFinalKills() {
        return finalKills;
    }

    public void setFinalKills(int finalKills) {
        this.finalKills = finalKills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getIronCollected() {
        return ironCollected;
    }

    public void setIronCollected(int ironCollected) {
        this.ironCollected = ironCollected;
    }

    public int getGoldCollected() {
        return goldCollected;
    }

    public void setGoldCollected(int goldCollected) {
        this.goldCollected = goldCollected;
    }

    public int getEmeraldCollected() {
        return emeraldCollected;
    }

    public void setEmeraldCollected(int emeraldCollected) {
        this.emeraldCollected = emeraldCollected;
    }
}
