package me.pycrs.bedwarsrecoded;

import org.bukkit.ChatColor;

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
        } else {
            return encapsulateStars(stars, null);
        }
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
}
