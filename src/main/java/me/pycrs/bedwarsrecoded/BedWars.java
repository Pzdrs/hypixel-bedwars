package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.commands.ShoutCommand;
import me.pycrs.bedwarsrecoded.listeners.AsyncChatListener;
import me.pycrs.bedwarsrecoded.listeners.PlayerJoinListener;
import me.pycrs.bedwarsrecoded.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public final class BedWars extends JavaPlugin {
    private static BedWars instance;
    private static Mode mode;
    private static boolean gameInProgress = false;
    private List<BTeam> teams;

    public PlayerJoinListener playerJoinEvent;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        init();

        mode = Utils.teamSizeToMode(getConfig().getInt("teamSize"));
        // Clear current teams
        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        teams = BTeam.initTeams();
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

    public List<BPlayer> getPlayers() {
        List<BPlayer> players = new ArrayList<>();
        teams.forEach(team -> players.addAll(team.getPlayers()));
        return players;
    }

    public static Mode getMode() {
        return mode;
    }

    public List<BTeam> getTeams() {
        return teams;
    }

    public static boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(boolean gameInProgress) {
        BedWars.gameInProgress = gameInProgress;
        Utils.distributePlayersToTeams(this);
        // TODO: 4/6/2021 Print the big ass welcome message
    }
}
