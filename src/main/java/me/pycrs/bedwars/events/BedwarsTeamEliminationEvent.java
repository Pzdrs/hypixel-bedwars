package me.pycrs.bedwars.events;

import me.pycrs.bedwars.entities.team.BedwarsTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsTeamEliminationEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private BedwarsTeam team;

    public BedwarsTeamEliminationEvent(BedwarsTeam team) {
        this.team = team;
    }

    public BedwarsTeam getTeam() {
        return team;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
