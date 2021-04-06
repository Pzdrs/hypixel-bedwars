package me.pycrs.bedwarsrecoded;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BPlayer {
    private UUID uuid;
    private Team team;
    private boolean shoutCoolDown;
    private int kills, finalKills, deaths, bedDestroys;

    public BPlayer(UUID uuid) {
        this.uuid = uuid;

        this.kills = 0;
        this.finalKills = 0;
        this.deaths = 0;
        this.bedDestroys = 0;
    }

    public void putOnShoutCoolDown() {
        this.shoutCoolDown = true;
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), bukkitTask -> {
            this.shoutCoolDown = false;
        }, BedWars.getInstance().getConfig().getInt("shoutCooldown"));
    }

    public void addKill() {
        kills++;
    }

    public void addFinalKill() {
        finalKills++;
    }

    public void addDeath() {
        deaths++;
    }

    public void addBedDestroy() {
        bedDestroys++;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public Team getTeam() {
        return team;
    }

    public boolean isOnShoutCoolDown() {
        return shoutCoolDown;
    }

    public int getKills() {
        return kills;
    }

    public int getFinalKills() {
        return finalKills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getBedDestroys() {
        return bedDestroys;
    }
}
