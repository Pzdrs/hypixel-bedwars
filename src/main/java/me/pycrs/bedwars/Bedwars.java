package me.pycrs.bedwars;

import me.pycrs.bedwars.commands.ShoutCommand;
import me.pycrs.bedwars.commands.StartCommand;
import me.pycrs.bedwars.entities.BedwarsMap;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.listeners.*;
import me.pycrs.bedwars.listeners.bedwars.BedwarsPlayerDeathListener;
import me.pycrs.bedwars.listeners.bedwars.BedwarsPlayerKillListener;
import me.pycrs.bedwars.listeners.bedwars.BedwarsPlayerRespawnListener;
import me.pycrs.bedwars.listeners.bedwars.BedwarsBedBreakListener;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.tasks.LobbyLoop;
import me.pycrs.bedwars.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Bedwars extends JavaPlugin {
    private static Bedwars instance;

    private BedwarsMap map;
    private List<BedwarsTeam> teams;

    private LobbyLoop lobbyLoop;
    private static GameLoop gameLoop;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        if (!Settings.loadPluginConfig(getConfig())) Bukkit.getPluginManager().disablePlugin(Bedwars.getInstance());
        init();

        // Server setup
        Utils.applyDefaultGamerules(Bukkit.getWorld("world"));

        // Map setup
        JSONObject map = BedwarsMap.loadJSON();
        JSONArray diamondsGens = map.getJSONArray("diamondGenerators");
        JSONArray emeraldGens = map.getJSONArray("emeraldGenerators");

        this.map = BedwarsMap.createMap(map);

        diamondsGens.forEach(object -> BedwarsMap.addDiamondGenerator(object, this.map));
        emeraldGens.forEach(object -> BedwarsMap.addEmeraldGenerator(object, this.map));

        // Teams setup
        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        teams = BedwarsTeam.initTeams(map.getJSONArray("teams"));
    }

    public static Bedwars getInstance() {
        return instance;
    }

    public static boolean isGameInProgress() {
        return gameLoop != null && !gameLoop.isCancelled();
    }

    public void startLobbyCountdown() {
        this.lobbyLoop = new LobbyLoop(this, Settings.lobbyCountdown);
        lobbyLoop.runTaskTimer(this, 0, 20);
    }

    public void startGame() {
        gameLoop = new GameLoop(this);
        gameLoop.runTaskTimer(this, 0, 20);
    }

    public List<BedwarsPlayer> getPlayers() {
        List<BedwarsPlayer> players = new ArrayList<>();
        teams.forEach(team -> players.addAll(team.getPlayers()));
        return players;
    }

    public BedwarsMap getMap() {
        return map;
    }

    public LobbyLoop getLobbyLoop() {
        return lobbyLoop;
    }

    public List<BedwarsTeam> getTeams() {
        return teams;
    }

    private void init() {
        new DisableFeaturesListener(this);
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);
        new AsyncPlayerPreLoginListener(this);
        new EntityDamageListener(this);
        new PlayerInteractEntityListener(this);
        new InventoryClickListener(this);
        new PlayerInteractListener(this);
        new EntityPickupItemListener(this);
        new BlockBreakPlaceListener(this);

        new BedwarsPlayerDeathListener(this);
        new BedwarsPlayerRespawnListener(this);
        new BedwarsPlayerKillListener(this);
        new BedwarsBedBreakListener(this);

        new ShoutCommand(this);
        new StartCommand(this);
    }

    public static boolean isSoloOrDoubles() {
        return Settings.mode.equals(Mode.SOLO) || Settings.mode.equals(Mode.DOUBLES);
    }
}
