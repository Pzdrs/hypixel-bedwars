package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import me.pycrs.bedwars.util.Experience;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class BedwarsPlayer implements Comparable<BedwarsPlayer> {
    public static Map<UUID, Integer> shoutCooldown = new HashMap<>();

    private final Bedwars plugin;
    private final Player player;
    private final BedwarsTeam team;
    private final PlayerEquipment equipment;
    private final PlayerStatistics statistics;
    private boolean spectating = false;
    private int level = 1445;

    public BedwarsPlayer(Player player, BedwarsTeam team) {
        this.plugin = Bedwars.getInstance();
        this.player = player;
        this.team = team;
        // Default load-out - only leather armor
        this.equipment = new PlayerEquipment(this, Armor.DEFAULT, Pickaxe.DIAMOND_PICKAXE, Axe.DIAMOND_AXE, true);
        this.statistics = new PlayerStatistics();

        // Fetching player's statistics from the official Hypixel API
        String apiKey = Settings.hypixelApiKey;
        if (apiKey != null) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try (CloseableHttpClient client = HttpClients.createDefault()) {
                    HttpResponse response = client.execute(new HttpGet("https://api.hypixel.net/player?key=" + apiKey + "&uuid=" + player.getUniqueId()));
                    JSONObject object = new JSONObject(Utils.streamToString(response.getEntity().getContent()));
                    if (object.getBoolean("success")) {
                        JSONObject stats = object.getJSONObject("player").getJSONObject("stats").getJSONObject("Bedwars");
                        this.level = (int) Utils.getBedWarsLevel(stats.getDouble("Experience"));
                    } else {
                        Bukkit.getLogger().severe("An unexpected error occurred while fetching Hypixel data for " +
                                player.getUniqueId() + ". Caused by: " + object.getString("cause"));
                    }
                } catch (IOException e) {
                    Bukkit.getLogger().severe("An unexpected error occurred while fetching Hypixel data for " + player.getUniqueId());
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * This method is invoked when a player dies by natural means, i.e. is not killed by other player
     */
    public void kill() {
        Bukkit.getPluginManager().callEvent(new BedwarsPlayerDeathEvent(plugin, this, player.getLastDamageCause()));
    }

    /**
     * This method is invoked when a player is killed by another player
     *
     * @param killer the killer
     */
    public void kill(Player killer) {
        Bukkit.getPluginManager().callEvent(new BedwarsPlayerKillEvent(plugin, this, player.getLastDamageCause(), toBedwarsPlayer(killer)));
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

            } else BedwarsPlayer.shoutCooldown.put(player.getUniqueId(), Settings.shoutCooldown);
        }, 0, 20);
    }

    public void setSpectator(boolean spectator) {
        this.spectating = spectator;
        Utils.applySpectator(player, spectator, plugin);
    }

    public int getLevel() {
        return level;
    }

    public PlayerStatistics getStatistics() {
        return statistics;
    }

    public BedwarsTeam getTeam() {
        return team;
    }

    public void teleportToBase() {
        player.teleport(team.getSpawn());
    }

    public void showLevel() {
        Experience.changeExp(player, Experience.getExpFromLevel(level));
    }

    public boolean isSpectating() {
        return spectating;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerEquipment getEquipment() {
        return equipment;
    }

    @Override
    public String toString() {
        return "BedwarsPlayer{" +
                "player=" + player +
                ", statistics=" + statistics +
                '}';
    }

    public static BedwarsPlayer toBedwarsPlayer(Player player) {
        return Bedwars.getInstance().getPlayers().stream().filter(bPlayer -> bPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public static Optional<BedwarsPlayer> of(Player player) {
        for (BedwarsPlayer bedwarsPlayer : Bedwars.getInstance().getPlayers()) {
            if (bedwarsPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return Optional.of(bedwarsPlayer);
        }
        return Optional.empty();
    }

    @Override
    public int compareTo(@NotNull BedwarsPlayer o) {
        return o.statistics.getCombinedKills() - this.statistics.getCombinedKills();
    }
}
