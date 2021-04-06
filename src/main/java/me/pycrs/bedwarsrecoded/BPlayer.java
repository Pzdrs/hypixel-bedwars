package me.pycrs.bedwarsrecoded;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BPlayer {
    private BedWars bedWars;
    private UUID uuid;
    private boolean shoutCoolDown;
    private int shoutCoolDownLeft;
    private int kills, finalKills, deaths, bedDestroys;

    public BPlayer(BedWars bedWars, UUID uuid) {
        this.bedWars = bedWars;
        this.uuid = uuid;
        this.shoutCoolDownLeft = bedWars.getConfig().getInt("shoutCooldown");

        this.kills = 0;
        this.finalKills = 0;
        this.deaths = 0;
        this.bedDestroys = 0;
    }

    public void putOnShoutCoolDown() {
        this.shoutCoolDownLeft = bedWars.getConfig().getInt("shoutCooldown");
        this.shoutCoolDown = true;
        Bukkit.getScheduler().runTaskTimer(bedWars, bukkitTask -> {
            if (shoutCoolDownLeft == 0) this.shoutCoolDown = false;
            shoutCoolDownLeft--;
        }, 0, 20);
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
        for (Team team : bedWars.getTeams())
            for (Player player : team.getPlayers())
                if (player.getUniqueId().equals(uuid)) return team;
        return null;
    }

    public Pair<Boolean, Integer> isOnShoutCoolDown() {
        return new Pair<>(shoutCoolDown, shoutCoolDownLeft);
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
