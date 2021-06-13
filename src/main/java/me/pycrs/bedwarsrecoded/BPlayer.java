package me.pycrs.bedwarsrecoded;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.AbstractMap;
import java.util.Map;

public class BPlayer {
    private Bedwars plugin;
    private Player player;
    private boolean shoutCoolDown;
    private boolean spectating = false;
    private int shoutCoolDownLeft;
    private int kills, finalKills, deaths, bedDestroys;

    public BPlayer(Player player) {
        this.plugin = Bedwars.getInstance();
        this.player = player;
        this.shoutCoolDownLeft = Bedwars.getInstance().getConfig().getInt("shoutCooldown");
        this.kills = 0;
        this.finalKills = 0;
        this.deaths = 0;
        this.bedDestroys = 0;
    }

    public void putOnShoutCoolDown() {
        this.shoutCoolDownLeft = Bedwars.getInstance().getConfig().getInt("shoutCooldown");
        this.shoutCoolDown = true;
        Bukkit.getScheduler().runTaskTimer(Bedwars.getInstance(), bukkitTask -> {
            if (shoutCoolDownLeft == 0) this.shoutCoolDown = false;
            shoutCoolDownLeft--;
        }, 0, 20);
    }

    public BTeam getTeam() {
        return BTeam.getPlayersTeam(player);
    }

    public void teleportToBase() {
        player.teleport(BTeam.getPlayersTeam(player).getSpawn());
    }

    public void setSpectator(boolean spectator) {
        this.spectating = spectator;
        // Better invisibility
        if (spectator) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
            plugin.getPlayers().forEach(bPlayer -> {
                if (!bPlayer.isSpectating())
                    bPlayer.getPlayer().hidePlayer(plugin, player);
            });
        } else {
            teleportToBase();
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            plugin.getPlayers().forEach(bPlayer -> {
                bPlayer.getPlayer().showPlayer(plugin, player);
            });
        }
        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.COMPASS));
        player.setHealth(20);
        player.setInvulnerable(spectator);
        player.setAllowFlight(spectator);
        player.setFlying(spectator);
    }

    public boolean isSpectating() {
        return spectating;
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

    public Map.Entry<Boolean, Integer> isOnShoutCoolDown() {
        return new AbstractMap.SimpleEntry<>(shoutCoolDown, shoutCoolDownLeft);
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
