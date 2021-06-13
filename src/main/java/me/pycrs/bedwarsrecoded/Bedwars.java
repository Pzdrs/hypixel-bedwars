package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.commands.ShoutCommand;
import me.pycrs.bedwarsrecoded.commands.StartCommand;
import me.pycrs.bedwarsrecoded.listeners.*;
import me.pycrs.bedwarsrecoded.tasks.GameLoop;
import me.pycrs.bedwarsrecoded.tasks.LobbyLoop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Bedwars extends JavaPlugin {
    private static Bedwars instance;
    private static Mode mode;

    private BedwarsMap map;
    private List<BTeam> teams;

    private LobbyLoop lobbyLoop;
    private static GameLoop gameLoop;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        init();

        // Server setup
        Utils.applyDefaultGamerules(Bukkit.getWorld("world"));
        mode = Utils.teamSizeToMode(getConfig().getInt("teamSize"));

        // Map setup
        JSONObject map = Utils.loadMapJSON();
        JSONArray diamondsGens = map.getJSONArray("diamondGenerators");
        JSONArray emeraldGens = map.getJSONArray("emeraldGenerators");

        this.map = BedwarsMap.createMap(map);

        diamondsGens.forEach(object -> BedwarsMap.addDiamondGenerator(object, this.map));
        emeraldGens.forEach(object -> BedwarsMap.addEmeraldGenerator(object, this.map));

        // Teams setup
        Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
        teams = BTeam.initTeams(map.getJSONArray("teams"));
    }

    public static Bedwars getInstance() {
        return instance;
    }

    public static boolean isGameInProgress() {
        return gameLoop != null && !gameLoop.isCancelled();
    }

    public void startLobbyCountdown() {
        this.lobbyLoop = new LobbyLoop(this, getConfig().getInt("lobbyCountdown"));
        lobbyLoop.runTaskTimer(this, 0, 20);
    }

    public void startGame() {
        gameLoop = new GameLoop(this);
        gameLoop.runTaskTimer(this, 0, 20);
    }

    public List<BPlayer> getPlayers() {
        List<BPlayer> players = new ArrayList<>();
        teams.forEach(team -> players.addAll(team.getPlayers()));
        return players;
    }

    public BPlayer getBPlayer(Player player) {
        return getPlayers().stream().filter(player1 -> player1.getPlayer().getUniqueId().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public BedwarsMap getMap() {
        return map;
    }

    public static Mode getMode() {
        return mode;
    }

    public LobbyLoop getLobbyLoop() {
        return lobbyLoop;
    }

    public List<BTeam> getTeams() {
        return teams;
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
        new PlayerInteractListener(this);
        new BlockBreakPlaceListener(this);

        new ShoutCommand(this);
        new StartCommand(this);
    }
}
