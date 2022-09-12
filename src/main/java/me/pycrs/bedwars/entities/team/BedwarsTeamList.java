package me.pycrs.bedwars.entities.team;

import me.pycrs.bedwars.util.BedwarsList;

import java.util.ArrayList;
import java.util.List;

/**
 * This mutable list takes care of keeping track of all the teams.
 */
public final class BedwarsTeamList extends BedwarsList<BedwarsTeam> {
    private static BedwarsTeamList TEAMS;

    public static BedwarsTeamList getList() {
        if (TEAMS == null) return new BedwarsTeamList(new ArrayList<>());
        return TEAMS;
    }

    public static void set(BedwarsTeamList players) {
        TEAMS = players;
    }

    public BedwarsTeamList(List<BedwarsTeam> teams) {
        super(teams);
    }

    /**
     * @return all teams that haven't been yet eliminated
     */
    public BedwarsTeamList alive() {
        return new BedwarsTeamList(elements.stream().filter(team -> !team.isEliminated()).toList());
    }
}
