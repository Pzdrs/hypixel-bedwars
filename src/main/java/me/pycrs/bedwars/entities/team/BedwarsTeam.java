package me.pycrs.bedwars.entities.team;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.events.BedwarsTeamEliminationEvent;
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
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class BedwarsTeam {
    private Bedwars plugin = Bedwars.getInstance();
    private Team team;
    private TeamColor teamColor;
    private TeamUpgrades upgrades;
    // The boolean value determines if the player is eliminated from the team, i.e. if true, the player is eliminated, if false they are not
    private Map<BedwarsPlayer, Boolean> players;
    private Forge forge;
    private Location spawn, teamChest, bedHead, bedFoot;
    private boolean hasBed = true;

    public BedwarsTeam(TeamColor teamColor, Location spawn, Location teamChest, Location bedHead, Location bedFoot, Forge forge) {
        this.players = new HashMap<>();
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
        players.put(bedwarsPlayer, false);
        player.playerListName(Component.text(player.getName(), teamColor.getColor()));
        team.addEntry(player.getName());
    }

    public void broadcastMessage(Component message) {
        players.forEach((bedwarsPlayer, eliminated) -> bedwarsPlayer.getPlayer().sendMessage(message));
    }

    public void broadcastTitle(Title title) {
        players.forEach((bedwarsPlayer, eliminated) -> {
            bedwarsPlayer.getPlayer().showTitle(title);
        });
    }

    public boolean isPartOfTeam(BedwarsPlayer player) {
        return players.containsKey(player);
    }

    public boolean isPartOfTeam(Player player) {
        for (Map.Entry<BedwarsPlayer, Boolean> entry : players.entrySet()) {
            if (entry.getKey().getPlayer().getUniqueId().equals(player.getUniqueId())) return true;
        }
        return false;
    }

    public Component getVictoryTeamMembersList() {
        Component component = teamColor.getFriendlyName().append(Component.text(" - ", NamedTextColor.GRAY));
        List<BedwarsPlayer> onlinePlayers = getOnlinePlayers();
        for (int i = 0; i < onlinePlayers.size(); i++) {
            component = component.append(onlinePlayers.get(i).getPlayer().displayName()).append(Component.text(i == onlinePlayers.size() - 1 ? "" : ", ", NamedTextColor.GRAY));
        }
        return component;
    }

    public void eliminatePlayer(BedwarsPlayer bedwarsPlayer) {
        players.put(bedwarsPlayer, true);
        if (isEliminated()) Bukkit.getServer().getPluginManager().callEvent(new BedwarsTeamEliminationEvent(this));
    }

    public void destroyBed(@Nullable BedwarsPlayer player) {
        if (!hasBed) return;
        hasBed = false;
        if (player == null) {
            getOnlinePlayers().forEach(bedwarsPlayer -> {
                bedwarsPlayer.getPlayer().sendMessage(Component.text("All beds have been destroyed!", NamedTextColor.RED, TextDecoration.BOLD));
                bedwarsPlayer.getPlayer().showTitle(Title.title(Component.text("BED DESTROYED!", NamedTextColor.RED), Component.text("All beds have been destroyed!")));
            });
        } else {
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
        if (getOnlinePlayers().size() == 0)
            Bukkit.getServer().getPluginManager().callEvent(new BedwarsTeamEliminationEvent(this));
    }

    public boolean hasBed() {
        return hasBed;
    }

    public Forge getForge() {
        return forge;
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

    public Map<BedwarsPlayer, Boolean> getPlayers() {
        return players;
    }

    public List<BedwarsPlayer> getOnlinePlayers() {
        return players.keySet().stream().filter(bedwarsPlayer -> bedwarsPlayer.getPlayer().isOnline()).collect(Collectors.toList());
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
        for (Map.Entry<BedwarsPlayer, Boolean> entry : players.entrySet()) {
            if (!entry.getValue()) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BedwarsTeam{" +
                "players=" + players +
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
        //removeEmptyTeams();
    }

    private static void removeEmptyTeams() {
        Iterator<BedwarsTeam> iterator = Bedwars.getInstance().getTeams().iterator();
        while (iterator.hasNext()) {
            BedwarsTeam team = iterator.next();
            if (team.players.size() == 0) {
                Bukkit.getServer().getPluginManager().callEvent(new BedwarsTeamEliminationEvent(team));
                iterator.remove();
            }
        }
    }
}
