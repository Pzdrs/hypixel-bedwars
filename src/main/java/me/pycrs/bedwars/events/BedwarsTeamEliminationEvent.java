package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsTeamEliminationEvent extends BedwarsTeamEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsTeamEliminationEvent(Bedwars plugin, BedwarsTeam team) {
        super(plugin, team);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
