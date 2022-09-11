package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.util.BedwarsList;

import java.util.List;

/**
 * This immutable list takes care of keeping track of all the players that were there, when
 * the game started. It does not change.
 */
public final class BedwarsPlayerList extends BedwarsList<BedwarsPlayer> {
    private static BedwarsPlayerList PLAYERS;

    public static BedwarsPlayerList getList() {
        return PLAYERS;
    }

    public static void set(BedwarsPlayerList players) {
        PLAYERS = players;
    }

    /**
     * Cache
     */
    private BedwarsPlayerList teamMembers;

    public BedwarsPlayerList(List<BedwarsPlayer> players) {
        super(List.copyOf(players));
    }

    public BedwarsPlayerList spectators() {
        return new BedwarsPlayerList(elements.stream()
                .filter(bedwarsPlayer -> bedwarsPlayer.isSpectating()).toList());
    }

    public BedwarsPlayerList nonSpectators() {
        return new BedwarsPlayerList(elements.stream()
                .filter(bedwarsPlayer -> !bedwarsPlayer.isSpectating()).toList());
    }

    /**
     * @param team team
     * @return all members of a given team (cached)
     */
    public BedwarsPlayerList teamMembers(BedwarsTeam team) {
        if (teamMembers == null)
            this.teamMembers = new BedwarsPlayerList(elements.stream().filter(team::isPartOfTeam).toList());
        return teamMembers;
    }
}
