package me.pycrs.bedwarsrecoded;

import com.google.gson.Gson;
import me.pycrs.bedwarsrecoded.commands.ShoutCommand;
import me.pycrs.bedwarsrecoded.commands.StartCommand;
import me.pycrs.bedwarsrecoded.listeners.*;
import me.pycrs.bedwarsrecoded.tasks.LobbyCountdown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class BedWars extends JavaPlugin {
    private static BedWars instance;
    private static Mode mode;
    public static boolean gameInProgress = false;
    private List<BTeam> teams;
    private LobbyCountdown lobbyCountdown;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        init();

        Gson gson = new Gson();

        try {
            Map<?, ?> map = gson.fromJson(Files.newBufferedReader(Paths.get("world/map.json")), Map.class);
            map.forEach((o, o2) -> {
                System.out.println(o);
                System.out.println(o2);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mode = Utils.teamSizeToMode(getConfig().getInt("teamSize"));
            // Clear current teams
            Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
            teams = BTeam.initTeams();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }

    public static BedWars getInstance() {
        return instance;
    }

    public List<BPlayer> getPlayers() {
        List<BPlayer> players = new ArrayList<>();
        teams.forEach(team -> players.addAll(team.getPlayers()));
        return players;
    }

    public BPlayer getBPlayer(Player player) {
        return getPlayers().stream().filter(player1 -> player1.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public BTeam getPlayersTeam(Player player) {
        for (BTeam team : teams) {
            for (BPlayer teamPlayer : team.getPlayers()) {
                if (teamPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return team;
            }
        }
        return null;
    }

    public static Mode getMode() {
        return mode;
    }

    public LobbyCountdown getLobbyCountdown() {
        return lobbyCountdown;
    }

    public List<BTeam> getTeams() {
        return teams;
    }

    public void startGame() {
        this.lobbyCountdown = new LobbyCountdown(this, getConfig().getInt("lobbyCountdown"));
        lobbyCountdown.runTaskTimer(this, 0, 20);
    }

    public void setGameInProgress(boolean gameInProgress) {
        BedWars.gameInProgress = gameInProgress;
        // TODO: 4/6/2021 Print the big ass welcome message
    }

    private void init() {
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);
        new BWPlayerDeathListener(this);
        new BWPlayerRespawnListener(this);
        new AsyncPlayerPreLoginListener(this);
        new FoodLevelChangeListener(this);
        new CreatureSpawnListener(this);
        new EntityDamageListener(this);
        new PlayerInteractEntityListener(this);
        new InventoryClickListener(this);

        new ShoutCommand(this);
        new StartCommand(this);
    }
}
