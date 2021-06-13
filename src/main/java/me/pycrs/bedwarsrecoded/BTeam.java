package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.generators.GoldGenerator;
import me.pycrs.bedwarsrecoded.generators.IronGenerator;
import me.pycrs.bedwarsrecoded.teamupgrades.TeamUpgrades;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BTeam {
    private Team team;
    private TeamColor teamColor;
    private TeamUpgrades upgrades;
    private Set<BPlayer> players;
    private IronGenerator ironGenerator;
    private GoldGenerator goldGenerator;
    private Location spawn, teamChest;
    private boolean hasBed = true;

    public BTeam(TeamColor teamColor, Location spawn, Location teamChest, IronGenerator ironGenerator, GoldGenerator goldGenerator) {
        this.players = new HashSet<>();
        this.team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamColor.name());
        // This is where u can make teams already have some upgrades from the beginning, useful for different game modes
        team.color(teamColor.getColor());
        this.upgrades = new TeamUpgrades();
        this.teamChest = teamChest;
        this.teamColor = teamColor;
        this.spawn = spawn;
        this.ironGenerator = ironGenerator;
        this.goldGenerator = goldGenerator;
    }

    public void addPlayer(Player player) {
        players.add(new BPlayer(player));
        player.playerListName(Component.text(player.getName(), teamColor.getColor()));
        team.addEntry(player.getName());
    }

    public void teamBroadcast(Component message) {
        players.forEach(player -> player.getPlayer().sendMessage(message));
    }

    public boolean isPartOfTeam(Player player) {
        for (BPlayer bPlayer : players) {
            if (bPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return true;
        }
        return false;
    }

    public Location getSpawn() {
        return spawn.clone().add(-.5, 0, .5);
    }

    public Team getTeam() {
        return team;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public Set<BPlayer> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "BTeam{" +
                "team=" + team +
                ", teamColor=" + teamColor +
                ", upgrades=" + upgrades +
                ", players=" + players +
                ", hasBed=" + hasBed +
                ", spawn=" + spawn +
                '}';
    }

    public static List<BTeam> initTeams(JSONArray config) {
        List<BTeam> teams = new ArrayList<>();
        for (TeamColor color : TeamColor.values()) {
            for (Object o : config) {
                JSONObject teamConfig = new JSONObject(o.toString());
                if (color.toString().equalsIgnoreCase(teamConfig.getString("color"))) {
                    JSONObject spawn = teamConfig.getJSONObject("spawn");
                    JSONObject teamChest = teamConfig.getJSONObject("teamChest");
                    JSONObject forge = teamConfig.getJSONObject("forge");
                    Location spawnLocation = new Location(
                            Bukkit.getWorld("world"),
                            spawn.getDouble("x"),
                            spawn.getDouble("y"),
                            spawn.getDouble("z"),
                            spawn.getFloat("yaw"),
                            spawn.getFloat("pitch")
                    );
                    Location teamChestLocation = new Location(
                            Bukkit.getWorld("world"),
                            teamChest.getDouble("x"),
                            teamChest.getDouble("y"),
                            teamChest.getDouble("z")
                    );
                    Location forgeLocation = new Location(
                            Bukkit.getWorld("world"),
                            forge.getDouble("x"),
                            forge.getDouble("y"),
                            forge.getDouble("z")
                    );

                    teams.add(new BTeam(color, spawnLocation, teamChestLocation,
                            new IronGenerator(forgeLocation),
                            new GoldGenerator(forgeLocation, Material.GOLD_INGOT)));
                    break;
                }
            }
        }
        return teams.stream().limit(BedWars.getMode().getAmountOfTeams()).collect(Collectors.toList());
    }

    public Location getTeamChest() {
        return teamChest;
    }

    public IronGenerator getIronGenerator() {
        return ironGenerator;
    }

    public GoldGenerator getGoldGenerator() {
        return goldGenerator;
    }

    public boolean isFull() {
        return players.size() == BedWars.getMode().getTeamSize();
    }

    public boolean isEliminated() {
        return !hasBed && players.size() == 0;
    }
}
