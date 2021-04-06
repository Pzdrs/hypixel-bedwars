package me.pycrs.bedwarsrecoded;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Team {
    private TeamColor color;
    private boolean hasBed;
    private List<Player> players;

    public Team(TeamColor color) {
        this.color = color;
        this.hasBed = true;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public TeamColor getColor() {
        return color;
    }

    public boolean isHasBed() {
        return hasBed;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "Team{" +
                "color=" + color +
                ", hasBed=" + hasBed +
                ", players=" + players +
                '}';
    }

    // TODO: 4/6/2021 team population
    public static void populateTeams(BedWars bedWars) {
        switch (bedWars.getMode()) {
            case SOLO:
                break;
            case DOUBLES:
                break;
            case TRIOS:
                break;
            case SQUADS:
                break;
        }
    }

    public static List<Team> initTeams(BedWars bedWars) {
        return Arrays.stream(TeamColor.values())
                .map(Team::new)
                .limit(bedWars.getMode().getAmountOfTeams())
                .collect(Collectors.toList());
    }
}
