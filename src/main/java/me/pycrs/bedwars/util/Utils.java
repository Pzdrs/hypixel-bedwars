package me.pycrs.bedwars.util;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.player.BedwarsPlayerList;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private Utils() {
        throw new AssertionError();
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
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
            BedwarsPlayer.PlayerListName.SPECTATOR.apply(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false, false));
            BedwarsPlayerList.getList().nonSpectators().forEach(p -> p.getPlayer().hidePlayer(plugin, player));
        } else {
            BedwarsPlayer.PlayerListName.IN_GAME.apply(player);
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            BedwarsPlayerList.getList().forEach(p -> p.getPlayer().showPlayer(plugin, player));
        }
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.setHealth(20);
        player.setInvulnerable(spectator);
        player.setAllowFlight(spectator);
        player.setFireTicks(0);
        player.setFlying(spectator);
    }

    public static <T> boolean atLeastOneEquals(T object, T[] objects) {
        for (T t : objects) {
            if (t == object) return true;
        }
        return false;
    }

    public static void sanitizePlayer(@NotNull Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.getInventory().clear();
        player.setLevel(0);
        player.setExp(0);
        player.setFireTicks(0);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setInvulnerable(true);
    }
}
