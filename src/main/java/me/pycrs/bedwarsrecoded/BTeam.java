package me.pycrs.bedwarsrecoded;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

public class BTeam {
    private Team team;
    private TeamColor teamColor;
    private Set<BPlayer> players;
    private boolean hasBed = true;

    public BTeam(TeamColor teamColor) {
        this.team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamColor.name());
        team.color(teamColor.getColor());
        this.teamColor = teamColor;
        this.players = new HashSet<>();
    }

    public void addPlayer(Player player) {
        players.add(new BPlayer(player));
        team.addEntry(player.getName());
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
