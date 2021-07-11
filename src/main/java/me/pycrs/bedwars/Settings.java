package me.pycrs.bedwars;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
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

        eventDiamondII = config.getInt("diamondII");
        eventDiamondIII = config.getInt("diamondIII");
        eventEmeraldII = config.getInt("emeraldII");
        eventEmeraldIII = config.getInt("emeraldIII");
        eventBedDestruction = config.getInt("bedDestruction");
        eventSuddenDeath = config.getInt("suddenDeath");
        eventGameEnd = config.getInt("gameEnd");

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
}
