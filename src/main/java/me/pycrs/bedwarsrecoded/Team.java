package me.pycrs.bedwarsrecoded;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Team {
    private TeamColor color;
    private boolean hasBed;
    private List<BPlayer> BPlayers;
    private List<TeamUpgrade> teamUpgrades;

    public Team(TeamColor color) {
        this.color = color;
        this.hasBed = true;
    }

    @Override
    public String toString() {
        return "Team{" +
                "color=" + color +
                ", hasBed=" + hasBed +
                ", players=" + BPlayers +
                ", teamUpgrades=" + teamUpgrades +
                '}';
    }

    public static List<Team> initTeams(BedWars bedWars) {
        return Arrays.stream(TeamColor.values())
                .map(Team::new)
                .limit(bedWars.getMode().getAmountOfTeams())
                .collect(Collectors.toList());
    }
}
