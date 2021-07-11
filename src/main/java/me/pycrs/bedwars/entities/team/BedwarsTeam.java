package me.pycrs.bedwars.entities.team;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.generators.Forge;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.teamupgrades.TeamUpgrades;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    private Bedwars plugin = Bedwars.getInstance();
    private Team team;
    private TeamColor teamColor;
    private TeamUpgrades upgrades;
    private Set<BedwarsPlayer> players;
    private Forge forge;
    private Location spawn, teamChest, bedHead, bedFoot;
    private boolean hasBed = true;

    public BedwarsTeam(TeamColor teamColor, Location spawn, Location teamChest, Location bedHead, Location bedFoot, Forge forge) {
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
        this.forge = forge;
        forge.setTeam(this);
    }

    public void addPlayer(Player player) {
        BedwarsPlayer bedwarsPlayer = new BedwarsPlayer(player, this);
        plugin.getPlayers().add(bedwarsPlayer);
        players.add(bedwarsPlayer);
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
        player.getStatistics().setBeds(player.getStatistics().getBeds() + 1);
        Bedwars.getInstance().getPlayers().forEach(bedwarsPlayer -> {
            if (isPartOfTeam(bedwarsPlayer)) {
                bedwarsPlayer.getPlayer().showTitle(
                        Title.title(Component.text("BED DESTROYED!", NamedTextColor.RED), Component.text("You will no longer respawn!")));
                bedwarsPlayer.getPlayer().sendMessage(Component.newline()
                        .append(Component.text("BED DESTRUCTION > ", Style.style(TextDecoration.BOLD)))
                        .append(Component.text("Your bed was destroyed by ", NamedTextColor.GRAY))
                        .append(player.getPlayer().displayName().color(player.getTeam().getTeamColor().getColor()))
                        .append(Component.text("!", NamedTextColor.GRAY))
                        .append(Component.newline()));
            } else {
                bedwarsPlayer.getPlayer().sendMessage(Component.newline()
                        .append(Component.text("BED DESTRUCTION > ", Style.style(TextDecoration.BOLD)))
                        .append(teamColor.getBedDisplay())
                        .append(Component.text(" was destroyed by ", NamedTextColor.GRAY))
                        .append(player.getPlayer().displayName().color(player.getTeam().getTeamColor().getColor()))
                        .append(Component.text("!", NamedTextColor.GRAY))
                        .append(Component.newline()));
            }
        });
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

    public Forge getIronGenerator() {
        return forge;
    }

    public boolean isFull() {
        return players.size() == Settings.mode.getTeamSize();
    }

    public boolean isEliminated() {
        return !hasBed && players.size() == 0;
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
                            new Forge(forgeLocation,
                                    Generator.getProperty("forgeIronCap", false),
                                    Generator.getProperty("forgeGoldCap", false))));
                    break;
                }
            }
        }
        return teams.stream().limit(Settings.mode.getAmountOfTeams()).collect(Collectors.toList());
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
