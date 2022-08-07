package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;

public abstract class BedwarsTeamEvent extends BedwarsEvent {
    protected final BedwarsTeam bedwarsTeam;

    public BedwarsTeamEvent(Bedwars plugin, BedwarsTeam bedwarsTeam) {
        super(plugin);
        this.bedwarsTeam = bedwarsTeam;
    }

    public BedwarsTeam getTeam() {
        return bedwarsTeam;
    }
}
