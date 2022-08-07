package me.pycrs.bedwars;

import me.pycrs.bedwars.commands.ShoutCommand;
import me.pycrs.bedwars.commands.StartCommand;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.listeners.*;
import me.pycrs.bedwars.listeners.bedwars.*;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.tasks.LobbyLoop;
import me.pycrs.bedwars.util.BedwarsMap;
import me.pycrs.bedwars.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Bedwars extends JavaPlugin {
    private static Bedwars instance;

    private BedwarsMap map;
    private List<BedwarsPlayer> players;
    private List<BedwarsTeam> teams;

    private LobbyLoop lobbyLoop;
    public static GameLoop gameLoop;

    /**
     * {@link Bedwars#gameFinished} is true when the game ends and the server is restarting soon, false otherwise
     * {@link Bedwars#gameFinished} is true when the game is in progress, false before and after the game
     */
    private static boolean gameFinished = false, gameInProgress = false;

    public static Bedwars getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        // Automatic reload
        final long lastModified = getFile().lastModified();

        new BukkitRunnable() {
            public void run() {
                if (getFile().lastModified() > lastModified) {
                    cancel();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload confirm");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "start");
                }
            }
        }.runTaskTimer(this, 0, 20);

        instance = this;
        saveDefaultConfig();
        if (!Settings.loadPluginConfig(getConfig())) Bukkit.getPluginManager().disablePlugin(Bedwars.getInstance());
        init();

        // Server setup
        Utils.applyDefaultGamerules(Bukkit.getWorld("world"));

        // Map setup
        Optional<JSONObject> potentialMap = BedwarsMap.loadJSON();
        // If the map data cannot be accessed, we can't continue - disable the plugin in that case
        if (potentialMap.isEmpty()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        JSONObject map = potentialMap.get();
        this.map = BedwarsMap.createMap(map);

        // Teams setup
        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        this.players = new ArrayList<>();
        this.teams = BedwarsTeam.initTeams(map.getJSONArray("teams"));
    }

    public static boolean isGameInProgress() {
        return gameInProgress;
    }

    public static boolean isGameFinished() {
        return gameFinished;
    }

    public LobbyLoop getLobbyLoop() {
        return lobbyLoop;
    }

    public List<BedwarsPlayer> getPlayers() {
        return players;
    }

    public BedwarsMap getMap() {
        return map;
    }

    public List<BedwarsTeam> getTeams() {
        return teams;
    }

    public void startLobbyCountdown() {
        this.lobbyLoop = new LobbyLoop(this, Settings.lobbyCountdown);
        lobbyLoop.runTaskTimer(this, 0, 20);
    }

    public static void setGameFinished(boolean gameFinished) {
        Bedwars.gameFinished = gameFinished;
    }

    public static void setGameInProgress(boolean gameInProgress) {
        Bedwars.gameInProgress = gameInProgress;
    }

    private void init() {
        new DisableFeaturesListener(this);
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);
        new EntityDamageListener(this);
        new PlayerInteractEntityListener(this);
        new InventoryClickListener(this);
        new PlayerInteractListener(this);
        new PlayerDropItemListener(this);
        new EntityPickupItemListener(this);
        new BlockBreakPlaceListener(this);

        new BedwarsGameStartListener(this);
        new BedwarsGameEndListener(this);
        new BedwarsTeamEliminationListener(this);
        new BedwarsPlayerDeathListener(this);
        new BedwarsPlayerRespawnListener(this);
        new BedwarsPlayerKillListener(this);
        new BedwarsBedBreakListener(this);

        new ShoutCommand(this);
        new StartCommand(this);
    }
}
