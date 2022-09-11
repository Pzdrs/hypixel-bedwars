package me.pycrs.bedwars;

import me.pycrs.bedwars.commands.ShoutCommand;
import me.pycrs.bedwars.commands.StartCommand;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.entities.team.BedwarsTeamList;
import me.pycrs.bedwars.listeners.*;
import me.pycrs.bedwars.listeners.bedwars.*;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.tasks.InventoryWatcher;
import me.pycrs.bedwars.tasks.LobbyLoop;
import me.pycrs.bedwars.util.BedwarsMap;
import me.pycrs.bedwars.scoreboard.LobbyScoreboard;
import me.pycrs.bedwars.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class Bedwars extends JavaPlugin {
    public enum GameStage {
        LOBBY_WAITING, LOBBY_COUNTDOWN, GAME_IN_PROGRESS, GAME_FINISHED;

        public boolean isGameInProgress() {
            return this == GAME_IN_PROGRESS;
        }

        public boolean isGameFinished() {
            return this == GAME_FINISHED;
        }

        public boolean isLobbyCountingDown() {
            return this == LOBBY_COUNTDOWN;
        }

        public boolean isLobby() {
            return this == LOBBY_WAITING || this == LOBBY_COUNTDOWN;
        }
    }

    private static Bedwars instance;

    private static GameStage gameStage = GameStage.LOBBY_WAITING;
    private static Mode mode;
    private static BedwarsMap map;

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

        // Find out what mode are we in, if an invalid mode is configured, halt the plugin
        Optional<Mode> potentialMode = Mode.of(getConfig().getInt("teamSize"));
        if (potentialMode.isEmpty()){
            Bukkit.getPluginManager().disablePlugin(Bedwars.getInstance());
            return;
        }
        mode = potentialMode.get();

        // Load settings from the config
        Settings.loadPluginConfig(getConfig());

        // Initialize all commands/listeners
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
        JSONObject mapObject = potentialMap.get();
        map = BedwarsMap.createMap(mapObject);

        // Teams setup
        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        BedwarsTeamList.set(new BedwarsTeamList(BedwarsTeam.initTeams(mapObject.getJSONArray("teams"))));

        getServer().getOnlinePlayers().forEach(LobbyScoreboard.get()::addPlayer);
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(LobbyScoreboard.get()::removePlayer);
    }

    public static GameStage getGameStage() {
        return gameStage;
    }

    public static Mode getMode() {
        return mode;
    }

    public static boolean isSoloOrDoubles() {
        return mode.equals(Mode.SOLO) || mode.equals(Mode.DOUBLES);
    }

    public static BedwarsMap getMap() {
        return map;
    }

    public static void setGameStage(GameStage gameStage) {
        Bedwars.gameStage = gameStage;
    }

    private void init() {
        new DisableFeaturesListener(this);
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);
        new EntityDamageListener(this);
        new PlayerInteractEntityListener(this);
        new InventoryInteractListener(this);
        new PlayerInteractListener(this);
        new PlayerDropItemListener(this);
        new EntityPickupItemListener(this);
        new BlockBreakPlaceListener(this);
        new AsyncPlayerPreLoginListener(this);

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
