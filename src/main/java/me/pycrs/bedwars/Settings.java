package me.pycrs.bedwars;

import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.configuration.file.FileConfiguration;

public final class Settings {
    private Settings() {
        throw new AssertionError();
    }

    public static final Component WELCOME_MESSAGE = Component.empty()
            .append(Utils.nAmountOfSymbols("\u25ac", 80).color(NamedTextColor.GREEN)).append(Component.newline())
            .append(Utils.nAmountOfSymbols(" ", 34)
                    .append(Component.text("Bed Wars", Style.style(TextDecoration.BOLD)))).append(Component.newline())
            .append(Component.newline())
            .append(Utils.nAmountOfSymbols(" ", 5)
                    .append(Component.text("Protect your bed and destroy the enemy beds.", NamedTextColor.YELLOW, TextDecoration.BOLD))).append(Component.newline())
            .append(Utils.nAmountOfSymbols(" ", 6)
                    .append(Component.text("Upgrade yourself and your team by collecting", NamedTextColor.YELLOW, TextDecoration.BOLD))).append(Component.newline())
            .append(Utils.nAmountOfSymbols(" ", 2)
                    .append(Component.text("Iron, Gold, Emeralds and Diamonds from generators", NamedTextColor.YELLOW, TextDecoration.BOLD))).append(Component.newline())
            .append(Utils.nAmountOfSymbols(" ", 18)
                    .append(Component.text("to access powerful upgrades.", NamedTextColor.YELLOW, TextDecoration.BOLD))).append(Component.newline())
            .append(Component.newline())
            .append(Utils.nAmountOfSymbols("\u25ac", 80).color(NamedTextColor.GREEN));

    public static Mode mode;
    public static int lobbyCountdown;
    public static int shoutCooldown;
    public static int playerTagPeriod;

    public static int eventDiamondII;
    public static int eventDiamondIII;
    public static int eventEmeraldII;
    public static int eventEmeraldIII;
    public static int eventBedDestruction;
    public static int eventSuddenDeath;
    public static int eventGameEnd;

    public static double forgeSplitRadius;

    public static String hypixelApiKey;

    /**
     * Load all the configuration to memory. If the values are not found in the config, defaults will be applied
     *
     * @param config Plugin's configuration
     * @return Successful config load
     */
    public static boolean loadPluginConfig(FileConfiguration config) {
        mode = teamSizeToMode(config.getInt("teamSize"));
        if (mode == null) return false;

        lobbyCountdown = config.getInt("lobbyCountdown", 20);
        shoutCooldown = config.getInt("shoutCooldown", 30);
        playerTagPeriod = config.getInt("playerTagPeriod", 5);

        eventDiamondII = config.getInt("events.diamondII");
        eventDiamondIII = config.getInt("events.diamondIII");
        eventEmeraldII = config.getInt("events.emeraldII");
        eventEmeraldIII = config.getInt("events.emeraldIII");
        eventBedDestruction = config.getInt("events.bedDestruction");
        eventSuddenDeath = config.getInt("events.suddenDeath");
        eventGameEnd = config.getInt("events.gameEnd");

        forgeSplitRadius = config.getDouble("forgeSplitRadius", 2.5);

        hypixelApiKey = config.getString("hypixelApiKey");
        return true;
    }

    private static Mode teamSizeToMode(int teamSize) {
        for (Mode mode : Mode.values()) {
            if (mode.getTeamSize() == teamSize) return mode;
        }
        Bedwars.getInstance().getLogger().severe("A team can't have " + teamSize + " players. Supported team sizes: 1, 2, 3 or 4");
        return null;
    }

    public static boolean isSoloOrDoubles() {
        return mode.equals(Mode.SOLO) || mode.equals(Mode.DOUBLES);
    }
}
