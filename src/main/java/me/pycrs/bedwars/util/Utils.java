package me.pycrs.bedwars.util;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utils {
    private Utils() {
        throw new AssertionError();
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // TODO: 4/6/2021 The new prestige colors beyond 1000 stars - not urgent
    public static Component formatStars(int stars) {
        if (inRange(stars, 0, 100)) {
            return encapsulateStars(stars, ChatColor.GRAY);
        } else if (inRange(stars, 100, 200)) {
            return encapsulateStars(stars, ChatColor.WHITE);
        } else if (inRange(stars, 200, 300)) {
            return encapsulateStars(stars, ChatColor.GOLD);
        } else if (inRange(stars, 300, 400)) {
            return encapsulateStars(stars, ChatColor.AQUA);
        } else if (inRange(stars, 400, 500)) {
            return encapsulateStars(stars, ChatColor.DARK_GREEN);
        } else if (inRange(stars, 500, 600)) {
            return encapsulateStars(stars, ChatColor.DARK_AQUA);
        } else if (inRange(stars, 600, 700)) {
            return encapsulateStars(stars, ChatColor.DARK_RED);
        } else if (inRange(stars, 700, 800)) {
            return encapsulateStars(stars, ChatColor.LIGHT_PURPLE);
        } else if (inRange(stars, 800, 900)) {
            return encapsulateStars(stars, ChatColor.BLUE);
        } else if (inRange(stars, 900, 1000)) {
            return encapsulateStars(stars, ChatColor.DARK_PURPLE);
        } else return encapsulateStars(stars, null);
    }

    private static Component encapsulateStars(int stars, ChatColor color) {
        if (color == null) {
            char[] star = String.valueOf(stars).toCharArray();
            return Component.text(color("&c[&6" + star[0] + "&e" + star[1] + "&a" + star[2] + "&b" + star[3] + "&d\u272B&5]&r "));
        }
        return Component.text(color + "[" + stars + "\u272B" + "] " + ChatColor.RESET);
    }

    private static boolean inRange(int stars, int low, int high) {
        return stars >= low && stars < high;
    }

    public static Component getTeamPrefix(BedwarsTeam team) {
        if (team == null) return Component.text("[teamName] ");
        return Component.text("[" + team.getTeamColor().name() + "] ", team.getTeamColor().getTextColor());
    }

    public static String commandArgsMessage(String[] args, int startIndex) {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < args.length; i++) {
            if (i == args.length - 1)
                builder.append(args[i]);
            else
                builder.append(args[i]).append(" ");
        }
        return builder.toString();
    }

    public static void inGameBroadcast(Component component) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(component));
    }

    public static String materialToFriendlyName(Material material) {
        return WordUtils.capitalizeFully(material.name().replace("_", " ").toLowerCase());
    }

    public static void applyDefaultGamerules(@Nullable World world) {
        if (world == null) return;
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
    }

    public static Component nAmountOfSymbols(String symbol, int n) {
        return Component.text(String.valueOf(symbol).repeat(Math.max(0, n)));
    }

    public static double getBedWarsLevel(double exp) {
        int level = 100 * ((int) (exp / 487000));
        exp = exp % 487000;
        if (exp < 500) return level + exp / 500;
        level++;
        if (exp < 1500) return level + (exp - 500) / 1000;
        level++;
        if (exp < 3500) return level + (exp - 1500) / 2000;
        level++;
        if (exp < 7000) return level + (exp - 3500) / 3500;
        level++;
        exp -= 7000;
        return level + exp / 5000;
    }

    public static String streamToString(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder s = new StringBuilder();
        try {
            int data = reader.read();
            while (data != -1) {
                s.append((char) data);
                data = reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    /**
     * A function that checks whether a given point, a block, is inside of an area centered around a block, the area's size is dictated by an offset on each axis
     *
     * @param point   The block that we are checking
     * @param center  The center of the area
     * @param xOffset Offset on the X axis
     * @param yOffset Offset on the Y axis
     * @param zOffset Offset on the Z axis
     * @return The point being inside of the given area
     */
    public static boolean isInArea(Location point, Location center, double xOffset, double yOffset, double zOffset) {
        return (point.getBlockX() > (center.getBlockX() - xOffset) && point.getBlockX() < (center.getBlockX() + xOffset)) &&
                (point.getBlockY() > (center.getBlockY() - yOffset) && point.getBlockY() < (center.getBlockY() + yOffset)) &&
                (point.getBlockZ() > (center.getBlockZ() - zOffset) && point.getBlockZ() < (center.getBlockZ() + zOffset));
    }

    /**
     * Format timer
     *
     * @param seconds Max. 3599s
     * @return Timer with format mm:ss
     */
    public static String formatTimer(int seconds) {
        return LocalTime.of(0, seconds / 60, seconds % 60).format(DateTimeFormatter.ofPattern("m:ss"));
    }

    public static void applySpectator(Player player, boolean spectator, Bedwars plugin) {
        // Better invisibility
        if (spectator) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
            plugin.getPlayers().forEach(p -> {
                if (!p.isSpectating()) p.getPlayer().hidePlayer(plugin, player);
            });
        } else {
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            plugin.getPlayers().forEach(p -> p.getPlayer().showPlayer(plugin, player));
        }
        player.setGameMode(spectator ? GameMode.SPECTATOR : GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setHealth(20);
        player.setInvulnerable(spectator);
        player.setAllowFlight(spectator);
        player.setFireTicks(0);
        player.setFlying(spectator);
    }
}
