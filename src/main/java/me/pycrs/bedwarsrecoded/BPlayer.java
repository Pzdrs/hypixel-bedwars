package me.pycrs.bedwarsrecoded;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BPlayer {
    private Player player;
    private boolean shoutCoolDown;
    private int shoutCoolDownLeft;
    private int kills, finalKills, deaths, bedDestroys;

    public BPlayer(Player player) {
        this.player = player;
        this.shoutCoolDownLeft = BedWars.getInstance().getConfig().getInt("shoutCooldown");
        this.kills = 0;
        this.finalKills = 0;
        this.deaths = 0;
        this.bedDestroys = 0;
    }

    public void putOnShoutCoolDown() {
        this.shoutCoolDownLeft = BedWars.getInstance().getConfig().getInt("shoutCooldown");
        this.shoutCoolDown = true;
        Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), bukkitTask -> {
            if (shoutCoolDownLeft == 0) this.shoutCoolDown = false;
            shoutCoolDownLeft--;
        }, 0, 20);
    }

    @Override
    public String toString() {
        return "BPlayer{" +
                "player=" + player +
                '}';
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
        return player;
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
