package me.pycrs.bedwarsrecoded;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BPlayer {
    public static Map<UUID, Integer> shoutCooldown = new HashMap<>();

    private Bedwars plugin;
    private Player player;
    private BTeam team;
    private boolean spectating = false;
    private int kills, finalKills, deaths, bedDestroys;

    public BPlayer(Player player, BTeam team) {
        this.plugin = Bedwars.getInstance();
        this.player = player;
        this.team = team;
        this.kills = 0;
        this.finalKills = 0;
        this.deaths = 0;
        this.bedDestroys = 0;
    }

    public void shout(Component component) {
        if (BPlayer.shoutCooldown.containsKey(player.getUniqueId())) {
            player.sendMessage(Component.text(Utils.color("&cYou must wait &e" +
                    BPlayer.shoutCooldown.get(player.getUniqueId()) + " &cseconds until you can use /shout again!")));
            return;
        }
        Utils.inGameBroadcast(component);
        // Take care of removing the player from cooldown and keep the time left updated
        Bukkit.getScheduler().runTaskTimer(plugin, bukkitTask -> {
            if (BPlayer.shoutCooldown.containsKey(player.getUniqueId())) {
                int current = BPlayer.shoutCooldown.get(player.getUniqueId());
                if (current == 1) {
                    BPlayer.shoutCooldown.remove(player.getUniqueId());
                    bukkitTask.cancel();
                } else BPlayer.shoutCooldown.put(player.getUniqueId(), current - 1);

            } else BPlayer.shoutCooldown.put(player.getUniqueId(), plugin.getConfig().getInt("shoutCooldown"));
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
        player.setFlying(spectator);
    }

    public BTeam getTeam() {
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

    public static BPlayer toBPlayer(Player player) {
        return Bedwars.getInstance().getPlayers().stream().filter(bPlayer -> bPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
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
