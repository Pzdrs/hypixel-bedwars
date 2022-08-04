package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BedwarsPlayer implements Comparable<BedwarsPlayer> {
    public static Map<UUID, Integer> shoutCooldown = new HashMap<>();

    private Bedwars plugin;
    private Player player;
    private BedwarsTeam team;
    private Equipment equipment;
    private PlayerStatistics statistics;
    private boolean spectating = false;
    private int level = 0;

    public BedwarsPlayer(Player player, BedwarsTeam team) {
        this.plugin = Bedwars.getInstance();
        this.player = player;
        this.team = team;
        this.equipment = new Equipment(Equipment.Armor.DEFAULT, Pickaxe.WOODEN_PICKAXE, Axe.WOODEN_AXE, false);
        this.statistics = new PlayerStatistics();

        // Fetching player's statistics from the official Hypixel API
        String apiKey = Settings.hypixelApiKey;
        if (apiKey != null) {
            CloseableHttpClient client = HttpClients.createDefault();
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
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

    public void setArmor() {
        ItemStack leggings, boots;
        ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                .setArmorColor(team.getTeamColor().getColor())
                .addEnchantment(Enchantment.WATER_WORKER, 1)
                .setUnbreakable(true)
                .build();

        ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setArmorColor(team.getTeamColor().getColor())
                .setUnbreakable(true)
                .build();

        switch (equipment.getArmor()) {
            case CHAIN_ARMOR:
                leggings = new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
                boots = new ItemBuilder(Material.CHAINMAIL_BOOTS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
            case IRON_ARMOR:
                leggings = new ItemBuilder(Material.IRON_LEGGINGS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
                boots = new ItemBuilder(Material.IRON_BOOTS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
            case DIAMOND_ARMOR:
                leggings = new ItemBuilder(Material.DIAMOND_LEGGINGS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
                boots = new ItemBuilder(Material.DIAMOND_BOOTS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
            case DEFAULT:
            default:
                leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
                boots = new ItemBuilder(Material.LEATHER_BOOTS)
                        .setArmorColor(team.getTeamColor().getColor())
                        .setUnbreakable(true)
                        .build();
        }

        Utils.setArmor(player, helmet, chestplate, leggings, boots, true);
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

    public boolean isSpectating() {
        return spectating;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "BedwarsPlayer{" +
                "player=" + player +
                ", statistics=" + statistics +
                '}';
    }

    public static BedwarsPlayer toBPlayer(Player player) {
        return Bedwars.getInstance().getPlayers().stream().filter(bPlayer -> bPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    @Override
    public int compareTo(@NotNull BedwarsPlayer o) {
        return o.statistics.getCombinedKills() - this.statistics.getCombinedKills();
    }
}
