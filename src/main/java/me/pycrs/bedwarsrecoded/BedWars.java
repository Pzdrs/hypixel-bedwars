package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.commands.ShoutCommand;
import me.pycrs.bedwarsrecoded.commands.StartCommand;
import me.pycrs.bedwarsrecoded.generator.DiamondGenerator;
import me.pycrs.bedwarsrecoded.generator.EmeraldGenerator;
import me.pycrs.bedwarsrecoded.listeners.*;
import me.pycrs.bedwarsrecoded.tasks.LobbyCountdown;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class BedWars extends JavaPlugin {
    private static BedWars instance;
    private static Mode mode;
    public static boolean gameInProgress = false;
    private List<BTeam> teams;
    private LobbyCountdown lobbyCountdown;
    private BedwarsMap map;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        init();

        // Extracting the crucial data from map.json
        try {
            JSONObject map = new JSONObject(Files.readString(Paths.get("world/map.json")));
            JSONObject lobbySpawn = map.getJSONObject("lobbySpawn");
            JSONArray diamondsGens = map.getJSONArray("diamondGenerators");
            JSONArray emeraldGens = map.getJSONArray("emeraldGenerators");

            this.map = new BedwarsMap(map.getString("name"), map.getJSONArray("mode").toList(),
                    new Location(
                            Bukkit.getWorld("world"),
                            lobbySpawn.getDouble("x"),
                            lobbySpawn.getDouble("y"),
                            lobbySpawn.getDouble("z"),
                            lobbySpawn.getFloat("yaw"),
                            lobbySpawn.getFloat("pitch")));

            diamondsGens.forEach(object -> BedwarsMap.addDiamondGenerator(object, this.map));
            emeraldGens.forEach(object -> BedwarsMap.addEmeraldGenerator(object, this.map));

            try {
                mode = Utils.teamSizeToMode(getConfig().getInt("teamSize"));
                // Clear current teams
                Bukkit.getScoreboardManager().getMainScoreboard().getTeams().forEach(Team::unregister);
                teams = BTeam.initTeams(map.getJSONArray("teams"));
                System.out.println(teams.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("There has been an error while parsing map.json");
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
