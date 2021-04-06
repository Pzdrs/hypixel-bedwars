package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.commands.ShoutCommand;
import me.pycrs.bedwarsrecoded.listeners.AsyncChatListener;
import me.pycrs.bedwarsrecoded.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedWars extends JavaPlugin {
    private static boolean gameInProgress = false;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        new PlayerJoinListener(this);
        new AsyncChatListener(this);

        new ShoutCommand(this);
    }

    public static boolean isGameInProgress() {
        return gameInProgress;
    }
}
