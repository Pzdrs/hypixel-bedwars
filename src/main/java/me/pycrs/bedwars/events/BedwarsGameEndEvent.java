package me.pycrs.bedwars.events;

import me.pycrs.bedwars.entities.team.BedwarsTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsGameEndEvent extends Event {
    public enum Result {
        NORMAL,
        EVERYONE_LEFT
    }

    private Result result;
    private BedwarsTeam team;

    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsGameEndEvent(Result result) {
        this.result = result;
    }

    public BedwarsGameEndEvent(Result result, BedwarsTeam team) {
        this.result = result;
        this.team = team;
    }

    public Result getResult() {
        return result;
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
