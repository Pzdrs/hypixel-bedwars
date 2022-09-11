package me.pycrs.bedwars.entities.team;

import me.pycrs.bedwars.util.BedwarsList;

import java.util.List;

/**
 * This mutable list takes care of keeping track of all the teams.
 */
public class BedwarsTeamList extends BedwarsList<BedwarsTeam> {
    private static BedwarsTeamList TEAMS;

    public static BedwarsTeamList getList() {
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

    // Modifiable list methods below

    @Override
    public BedwarsTeam set(int index, BedwarsTeam element) {
        return elements.set(index, element);
    }

    @Override
    public BedwarsTeam remove(int index) {
        return elements.remove(index);
    }

    @Override
    public boolean add(BedwarsTeam team) {
        return elements.add(team);
    }
}
