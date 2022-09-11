package me.pycrs.bedwars.util;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import org.jetbrains.annotations.Unmodifiable;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Predicate;

@Unmodifiable
public class BedwarsPlayerList extends AbstractList<BedwarsPlayer> {
    private final List<BedwarsPlayer> players;
    private BedwarsPlayerList spectators, nonSpectators, sorted, teamMembers;

    public BedwarsPlayerList(List<BedwarsPlayer> players) {
        this.players = List.copyOf(players);
    }

    public List<BedwarsPlayer> all() {
        return players;
    }

    public BedwarsPlayerList filter(Predicate<? super BedwarsPlayer> predicate) {
        return new BedwarsPlayerList(stream().filter(predicate).toList());
    }

    public BedwarsPlayerList spectators() {
        if (spectators == null)
            this.spectators = new BedwarsPlayerList(players.stream()
                    .filter(bedwarsPlayer -> bedwarsPlayer.isSpectating()).toList());
        return spectators;
    }

    public BedwarsPlayerList nonSpectators() {
        if (nonSpectators == null)
            this.nonSpectators = new BedwarsPlayerList(players.stream()
                    .filter(bedwarsPlayer -> !bedwarsPlayer.isSpectating()).toList());
        return nonSpectators;
    }

    public BedwarsPlayerList teamMembers(BedwarsTeam team) {
        if (teamMembers == null)
            this.teamMembers = new BedwarsPlayerList(players.stream().filter(team::isPartOfTeam).toList());
        return teamMembers;
    }

    public BedwarsPlayerList sorted() {
        if (sorted == null)
            this.sorted = new BedwarsPlayerList(players.stream().sorted().toList());
        return sorted;
    }

    @Override
    public BedwarsPlayer get(int index) {
        return players.get(index);
    }

    @Override
    public int size() {
        return players.size();
    }
}
