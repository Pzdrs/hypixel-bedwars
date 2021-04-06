package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.commands.ShoutCommand;
import me.pycrs.bedwarsrecoded.listeners.AsyncChatListener;
import me.pycrs.bedwarsrecoded.listeners.PlayerJoinListener;
import me.pycrs.bedwarsrecoded.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public final class BedWars extends JavaPlugin {
    private static BedWars instance;
    private Mode mode;
    private static boolean gameInProgress = false;
    private List<BPlayer> BPlayers;
    private List<Team> teams;

    public PlayerJoinListener playerJoinEvent;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        init();

        mode = Utils.teamSizeToMode(getConfig().getInt("teamSize"));
        teams = Team.initTeams(this);
        teams.forEach(System.out::println);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BedWars getInstance() {
        return instance;
    }

    private void init() {
        this.playerJoinEvent = new PlayerJoinListener(this);
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

    public void setGameInProgress(boolean gameInProgress) {
        BedWars.gameInProgress = gameInProgress;
        this.BPlayers = getServer().getOnlinePlayers().stream().map(player -> new BPlayer(player.getUniqueId())).collect(Collectors.toList());
        // TODO: 4/6/2021 init game
    }
}
