package me.pycrs.bedwarsrecoded;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Utils {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // TODO: 4/6/2021 The new prestige colors beyond 1000 stars - not urgent
    public static String formatStars(int stars) {
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

    private static String encapsulateStars(int stars, ChatColor color) {
        if (color == null) {
            char[] star = String.valueOf(stars).toCharArray();
            return color("&c[&6" + star[0] + "&e" + star[1] + "&a" + star[2] + "&b" + star[3] + "&d\u272B&5]&r");
        }
        return color + "[" + stars + "\u272B" + "]" + ChatColor.RESET;
    }

    private static boolean inRange(int stars, int low, int high) {
        return stars >= low && stars < high;
    }

    public static Component getTeamPrefix(BedwarsTeam team) {
        if (team == null) return Component.text("[teamName] ");
        return Component.text("[" + team.getTeamColor().name() + "] ", team.getTeamColor().getColor());
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

    public static Mode teamSizeToMode(int teamSize) {
        for (Mode mode : Mode.values()) {
            if (mode.getTeamSize() == teamSize) return mode;
        }
        Bedwars.getInstance().getLogger().severe("A team can't have " + teamSize + " players. Supported team sizes: 1, 2, 3 or 4");
        return null;
    }

    public static int getMaterialAmount(Inventory inventory, Material material) {
        int amount = 0;
        for (ItemStack content : inventory.getContents()) {
            if (content != null)
                if (content.getType().equals(material)) amount += content.getAmount();
        }
        return amount;
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
}
