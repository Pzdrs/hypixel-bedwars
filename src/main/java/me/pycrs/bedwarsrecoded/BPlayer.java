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
    private BTeam team;
    private boolean shoutCoolDown;
    private boolean spectating = false;
    private int shoutCoolDownLeft;
    private int kills, finalKills, deaths, bedDestroys;

    public BPlayer(Player player, BTeam team) {
        this.plugin = Bedwars.getInstance();
        this.player = player;
        this.team = team;
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

    public BTeam getTeam() {
        return team;
    }

    public void teleportToBase() {
        player.teleport(team.getSpawn());
    }

    public Map.Entry<Boolean, Integer> isOnShoutCoolDown() {
        return new AbstractMap.SimpleEntry<>(shoutCoolDown, shoutCoolDownLeft);
    }

    public boolean isSpectating() {
        return spectating;
    }

    public Player getPlayer() {
        return player;
    }

    public static BTeam getPlayersTeam(Player player) {
        for (BTeam team : Bedwars.getInstance().getTeams()) {
            for (BPlayer teamPlayer : team.getPlayers()) {
                if (teamPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return team;
            }
        }
        return null;
    }
}
