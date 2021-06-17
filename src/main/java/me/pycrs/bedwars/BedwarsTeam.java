package me.pycrs.bedwars;

import me.pycrs.bedwars.generators.GoldGenerator;
import me.pycrs.bedwars.generators.IronGenerator;
import me.pycrs.bedwars.teamupgrades.TeamUpgrades;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
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

public class BedwarsTeam {
    private Team team;
    private TeamColor teamColor;
    private TeamUpgrades upgrades;
    private Set<BedwarsPlayer> players;
    private IronGenerator ironGenerator;
    private GoldGenerator goldGenerator;
    private Location spawn, teamChest, bedHead, bedFoot;
    private boolean hasBed = true;

    public BedwarsTeam(TeamColor teamColor, Location spawn, Location teamChest, Location bedHead, Location bedFoot, IronGenerator ironGenerator, GoldGenerator goldGenerator) {
        this.players = new HashSet<>();
        this.team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamColor.name());
        // This is where u can make teams already have some upgrades from the beginning, useful for different game modes
        team.color(teamColor.getColor());
        this.upgrades = new TeamUpgrades();
        this.teamChest = teamChest;
        this.teamColor = teamColor;
        this.spawn = spawn;
        this.bedHead = bedHead;
        this.bedFoot = bedFoot;
        this.ironGenerator = ironGenerator;
        this.goldGenerator = goldGenerator;
    }

    public void addPlayer(Player player) {
        players.add(new BedwarsPlayer(player, this));
        player.playerListName(Component.text(player.getName(), teamColor.getColor()));
        team.addEntry(player.getName());
    }

    public void teamBroadcast(Component message) {
        players.forEach(player -> player.getPlayer().sendMessage(message));
    }

    public boolean isPartOfTeam(BedwarsPlayer player) {
        return players.contains(player);
    }

    public boolean isPartOfTeam(Player player) {
        for (BedwarsPlayer bedwarsPlayer : players) {
            if (bedwarsPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return true;
        }
        return false;
    }

    public void destroyBed(BedwarsPlayer player) {
        if (!hasBed) return;
        hasBed = false;
        player.setBeds(player.getBeds() + 1);
        players.forEach(bedwarsPlayer -> bedwarsPlayer.getPlayer().showTitle(
                Title.title(Component.text("BED DESTROYED!", NamedTextColor.RED), Component.text("You will no longer respawn!"))));
        Utils.inGameBroadcast(Component.newline()
                .append(Component.text("BED DESTRUCTION > ", Style.style(TextDecoration.BOLD)))
                .append(teamColor.getBedDisplay())
                .append(Component.text(" was destroyed by ", NamedTextColor.GRAY))
                .append(player.getPlayer().displayName().color(player.getTeam().getTeamColor().getColor()))
                .append(Component.text("!", NamedTextColor.GRAY))
                .append(Component.newline()));
    }

    public boolean hasBed() {
        return hasBed;
    }

    public Location getSpawn() {
        return spawn.clone().add(-.5, 0, .5);
    }

    public Location getBedFoot() {
        return bedFoot;
    }

    public Location getBedHead() {
        return bedHead;
    }

    public Team getTeam() {
        return team;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public Set<BedwarsPlayer> getPlayers() {
        return players;
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
        return players.size() == Bedwars.getMode().getTeamSize();
    }

    public boolean isEliminated() {
        return !hasBed && players.size() == 0;
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

    public static List<BedwarsTeam> initTeams(JSONArray config) {
        List<BedwarsTeam> teams = new ArrayList<>();
        for (TeamColor color : TeamColor.values()) {
            for (Object o : config) {
                JSONObject teamConfig = new JSONObject(o.toString());
                if (color.toString().equalsIgnoreCase(teamConfig.getString("color"))) {
                    JSONObject spawn = teamConfig.getJSONObject("spawn");
                    JSONObject teamChest = teamConfig.getJSONObject("teamChest");
                    JSONObject forge = teamConfig.getJSONObject("forge");
                    JSONObject bedHead = teamConfig.getJSONObject("bed").getJSONObject("head");
                    JSONObject bedFoot = teamConfig.getJSONObject("bed").getJSONObject("foot");
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
                    Location bedHeadLocation = new Location(
                            Bukkit.getWorld("world"),
                            bedHead.getDouble("x"),
                            bedHead.getDouble("y"),
                            bedHead.getDouble("z")
                    );
                    Location bedFootLocation = new Location(
                            Bukkit.getWorld("world"),
                            bedFoot.getDouble("x"),
                            bedFoot.getDouble("y"),
                            bedFoot.getDouble("z")
                    );

                    teams.add(new BedwarsTeam(color, spawnLocation, teamChestLocation, bedHeadLocation, bedFootLocation,
                            new IronGenerator(forgeLocation),
                            new GoldGenerator(forgeLocation, Material.GOLD_INGOT)));
                    break;
                }
            }
        }
        return teams.stream().limit(Bedwars.getMode().getAmountOfTeams()).collect(Collectors.toList());
    }

    public static void distributePlayers() {
        Bedwars.getInstance().getServer().getOnlinePlayers().forEach(player -> {
            for (BedwarsTeam team : Bedwars.getInstance().getTeams()) {
                if (!team.isFull()) {
                    team.addPlayer(player);
                    break;
                }
            }
        });
    }
}
