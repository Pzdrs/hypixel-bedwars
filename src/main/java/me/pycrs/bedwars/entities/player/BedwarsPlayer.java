package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.equipment.types.Armor;
import me.pycrs.bedwars.entities.player.equipment.types.Axe;
import me.pycrs.bedwars.entities.player.equipment.types.Pickaxe;
import me.pycrs.bedwars.entities.player.equipment.PlayerEquipment;
import me.pycrs.bedwars.entities.player.level.HypixelBedwarsLevel;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
    private Player player;
    private final BedwarsTeam team;
    private final PlayerEquipment equipment;
    private final PlayerStatistics statistics;
    private HypixelBedwarsLevel hypixelLevel = HypixelBedwarsLevel.DEFAULT;
    private boolean spectating = false;

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
                    HttpResponse response = client.execute(new HttpGet(String.format("https://api.hypixel.net/player?key=%s&uuid=%s",
                            apiKey, player.getUniqueId())));
                    JSONObject object = new JSONObject(Utils.streamToString(response.getEntity().getContent()));
                    if (object.getBoolean("success")) {
                        JSONObject stats = object.getJSONObject("player").getJSONObject("stats").getJSONObject("Bedwars");
                        setHypixelLevel(stats.getDouble("Experience"));
                    } else {
                        Bukkit.getLogger().severe(
                                String.format("An unexpected error occurred while fetching Hypixel data for %s. Caused by: %s",
                                        player.getUniqueId(), object.getString("cause")));
                    }
                } catch (IOException e) {
                    Bukkit.getLogger().severe(
                            String.format("An unexpected error occurred while fetching Hypixel data for %s. Caused by: %s",
                                    player.getUniqueId(), e.getLocalizedMessage()));
                }
            });
        }
    }

    /**
     * This method is used to assign value to {@link BedwarsPlayer#hypixelLevel} instead of assigning it directly,
     * due to the data fetching process being asynchronous
     *
     * @param experience experience
     */
    private void setHypixelLevel(double experience) {
        this.hypixelLevel = new HypixelBedwarsLevel(experience);
        showLevel();
    }

    /**
     * If a player leaves, this instance is still holding onto the player's object, after they rejoin, a
     * new object is Player object is created and the old one becomes useless
     *
     * @param player the new Player object
     */
    public void updatePlayerObject(Player player) {
        this.player = player;
    }

    /**
     * This method is invoked when a player dies by natural means, i.e. is not killed by other player
     */
    public void kill() {
        Bukkit.getPluginManager().callEvent(new BedwarsPlayerDeathEvent(plugin, this, player.getLastDamageCause()));
    }

    /**
     * This method is invoked when a player is killed by the server thus, no death cause is specified
     */
    public void killCauseless() {
        Bukkit.getPluginManager().callEvent(new BedwarsPlayerDeathEvent(plugin, this, null));
    }

    /**
     * This method is invoked when a player is killed by another player
     *
     * @param killer the killer
     */
    public void kill(Player killer) {
        Bukkit.getPluginManager().callEvent(new BedwarsPlayerKillEvent(plugin, this, player.getLastDamageCause(), toBedwarsPlayer(killer)));
    }

    public boolean isEliminated() {
        return !team.getNonEliminatedPlayers().contains(this);
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
        /*player.playerListName(spectator ?
                player.displayName().color(NamedTextColor.GRAY) :
                Component.empty()
                        .append(team.getTeamColor().getTeamLetterBold())
                        .append(Component.space())
                        .append(player.displayName()));*/
        Utils.applySpectator(player, spectator, plugin);
    }

    public HypixelBedwarsLevel getLevel() {
        return hypixelLevel;
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
        hypixelLevel.show(player);
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

    /**
     * @deprecated Legacy method, use {@link BedwarsPlayer#of(Player)} instead
     */
    public static BedwarsPlayer toBedwarsPlayer(Player player) {
        return Bedwars.getInstance().getPlayers().stream().filter(bPlayer -> bPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    /**
     * Null-safe player spectator check
     *
     * @return true if the player is spectating, false otherwise (including the possibility of the passed in player being null)
     */
    public static boolean isSpectating(Player player) {
        Optional<BedwarsPlayer> potentialPlayer = of(player);
        if (potentialPlayer.isEmpty()) return false;
        return potentialPlayer.get().isSpectating();
    }

    public static Optional<BedwarsPlayer> of(Player player) {
        for (BedwarsPlayer bedwarsPlayer : Bedwars.getInstance().getPlayers()) {
            if (bedwarsPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return Optional.of(bedwarsPlayer);
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BedwarsPlayer that)) return false;
        return player.getUniqueId().equals(that.player.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public int compareTo(@NotNull BedwarsPlayer o) {
        return o.statistics.getCombinedKills() - this.statistics.getCombinedKills();
    }
}
