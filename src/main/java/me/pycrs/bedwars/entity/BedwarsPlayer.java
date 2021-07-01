package me.pycrs.bedwars.entity;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class BedwarsPlayer {
    public static Map<UUID, Integer> shoutCooldown = new HashMap<>();

    private Bedwars plugin;
    private Player player;
    private BedwarsTeam team;
    private boolean spectating = false;
    private int kills, finalKills, deaths, beds;
    private int level = new Random().nextInt(1000);

    public BedwarsPlayer(Player player, BedwarsTeam team) {
        this.plugin = Bedwars.getInstance();
        this.player = player;
        this.team = team;
        this.kills = 0;
        this.finalKills = 0;
        this.deaths = 0;
        this.beds = 0;

        String apiKey = plugin.getConfig().getString("hypixelApiKey");
        if (apiKey != null) {
            // TODO: 6/15/2021 fetch player's bedwars experience from api.hypixel.net
        }
    }

    public void shout(Component component) {
        if (BedwarsPlayer.shoutCooldown.containsKey(player.getUniqueId())) {
            player.sendMessage(Component.text(Utils.color("&cYou must wait &e" +
                    BedwarsPlayer.shoutCooldown.get(player.getUniqueId()) + " &cseconds until you can use /shout again!")));
            return;
        }
        Utils.inGameBroadcast(component);
        // Take care of removing the player from cooldown and keep the time left updated
        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            if (BedwarsPlayer.shoutCooldown.containsKey(player.getUniqueId())) {
                int current = BedwarsPlayer.shoutCooldown.get(player.getUniqueId());
                if (current == 1) {
                    BedwarsPlayer.shoutCooldown.remove(player.getUniqueId());
                    bukkitTask.cancel();
                } else BedwarsPlayer.shoutCooldown.put(player.getUniqueId(), current - 1);

            } else BedwarsPlayer.shoutCooldown.put(player.getUniqueId(), plugin.getConfig().getInt("shoutCooldown"));
        }, 0, 20);
    }

    public void setSpectator(boolean spectator) {
        this.spectating = spectator;
        // Better invisibility
        if (spectator) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
            plugin.getPlayers().forEach(player -> {
                if (!player.isSpectating()) player.getPlayer().hidePlayer(plugin, this.player);
            });
        } else {
            teleportToBase();
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            plugin.getPlayers().forEach(player -> player.getPlayer().showPlayer(plugin, this.player));
        }
        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.COMPASS));
        player.setHealth(20);
        player.setInvulnerable(spectator);
        player.setAllowFlight(spectator);
        player.setFireTicks(0);
        player.setFlying(spectator);
    }

    public int getLevel() {
        return level;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
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

    public BedwarsTeam getTeam() {
        return team;
    }

    public void teleportToBase() {
        player.teleport(team.getSpawn());
    }

    public boolean isSpectating() {
        return spectating;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "BedwarsPlayer{" +
                "plugin=" + plugin +
                ", player=" + player +
                ", team=" + team +
                ", spectating=" + spectating +
                ", kills=" + kills +
                ", finalKills=" + finalKills +
                ", deaths=" + deaths +
                ", beds=" + beds +
                ", level=" + level +
                '}';
    }

    public static BedwarsPlayer toBPlayer(Player player) {
        return Bedwars.getInstance().getPlayers().stream().filter(bPlayer -> bPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public static BedwarsTeam getPlayersTeam(Player player) {
        for (BedwarsTeam team : Bedwars.getInstance().getTeams()) {
            for (BedwarsPlayer teamPlayer : team.getPlayers()) {
                if (teamPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return team;
            }
        }
        return null;
    }
}
