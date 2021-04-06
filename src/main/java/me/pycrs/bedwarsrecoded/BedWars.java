package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.commands.ShoutCommand;
import me.pycrs.bedwarsrecoded.listeners.AsyncChatListener;
import me.pycrs.bedwarsrecoded.listeners.PlayerJoinListener;
import me.pycrs.bedwarsrecoded.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedWars extends JavaPlugin {
    private static boolean gameInProgress = false;
    private Mode mode;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        init();

        mode = Utils.teamSizeToMode(getConfig().getInt("teamSize"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);

        new ShoutCommand(this);
    }

    public Mode getMode() {
        return mode;
    }

    public static boolean isGameInProgress() {
        return gameInProgress;
    }
}
