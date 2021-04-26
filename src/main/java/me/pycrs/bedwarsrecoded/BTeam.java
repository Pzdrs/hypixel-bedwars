package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.teamUpgrades.TeamUpgrades;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BTeam {
    private Team team;
    private TeamColor teamColor;
    private TeamUpgrades upgrades;
    private Set<BPlayer> players;
    private boolean hasBed = true;

    public BTeam(TeamColor teamColor) {
        this.team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamColor.name());
        // This is where u can make teams already have some upgrades from the beginning, useful for different game modes
        this.upgrades = new TeamUpgrades();
        team.color(teamColor.getColor());
        this.teamColor = teamColor;
        this.players = new HashSet<>();
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

    public TeamUpgrades getUpgrades() {
        return upgrades;
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

    public boolean isHasBed() {
        return hasBed;
    }

    @Override
    public String toString() {
        return "BTeam{" +
                "team=" + team +
                ", players=" + players +
                ", hasBed=" + hasBed +
                '}';
    }

    public static List<BTeam> initTeams() {
        return Arrays.stream(TeamColor.values())
                .map(BTeam::new)
                .limit(BedWars.getMode().getAmountOfTeams())
                .collect(Collectors.toList());
    }

    public boolean isFull() {
        return players.size() == BedWars.getMode().getTeamSize();
    }
}
