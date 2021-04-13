package me.pycrs.bedwarsrecoded;

import javafx.util.Pair;
import me.pycrs.bedwarsrecoded.exceptions.InvalidTeamSizeException;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

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

    public static String materialToFriendlyName(Material material) {
        return WordUtils.capitalize(material.name().toLowerCase().replace("_", " "));
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

    public static Component getTeamPrefix(BTeam team) {
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

    public static BPlayer isolateByUUID(List<BPlayer> players, Player player) {
        return players.stream().filter(bPlayer -> bPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public static void inGameBroadcast(Component component) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(component));
    }

    public static Mode teamSizeToMode(int teamSize) {
        for (Mode mode : Mode.values()) {
            if (mode.getTeamSize() == teamSize) return mode;
        }
        try {
            throw new InvalidTeamSizeException(teamSize);
        } catch (InvalidTeamSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void distributePlayersToTeams(BedWars plugin) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            for (BTeam team : plugin.getTeams()) {
                if (!team.isFull()) {
                    team.addPlayer(player);
                    break;
                }
            }
        });
    }

    public static boolean isLobbyCountdownInProgress(BedWars plugin) {
        return plugin.getLobbyCountdown() != null && !plugin.getLobbyCountdown().isCancelled();
    }

    public static boolean canAfford(Player player, Pair<BWCurrency, Integer> cost) {
        HashMap<Material, Integer> resources = new HashMap<>();
        for (ItemStack content : player.getInventory().getContents()) {
            if (content == null) break;
            if (resources.containsKey(content.getType())) {
                resources.put(content.getType(), resources.get(content.getType()) + content.getAmount());
                continue;
            }
            resources.put(content.getType(), content.getAmount());
        }
        return resources.get(cost.getKey().getMaterial()) != null && resources.get(cost.getKey().getMaterial()) >= cost.getValue();
    }
}
