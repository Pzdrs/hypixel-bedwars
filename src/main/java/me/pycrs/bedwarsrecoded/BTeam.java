package me.pycrs.bedwarsrecoded;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BTeam {
    private Team team;
    private List<BPlayer> players;
    private boolean hasBed;

    public BTeam(TeamColor teamColor) {
        this.players = new ArrayList<>();
        this.hasBed = true;
    }

    public void addPlayer(BPlayer player) {
        players.add(player);
        team.addEntry(player.getPlayer().getName());
    }

    public List<BPlayer> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "BTeam{" +
                "team=" + team +
                ", players=" + players +
                ", hasBed=" + hasBed +
                '}';
    }

    public static List<BTeam> initTeams(BedWars bedWars) {
        return Arrays.stream(TeamColor.values())
                .map(BTeam::new)
                .limit(bedWars.getMode().getAmountOfTeams())
                .collect(Collectors.toList());
    }
}
