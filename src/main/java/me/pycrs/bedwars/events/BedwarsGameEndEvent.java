package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsGameEndEvent extends BedwarsEvent {
    public enum Result {
        NORMAL,
        GAME_END,
        EVERYONE_LEFT
    }

    private final Result result;
    private BedwarsTeam team;

    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsGameEndEvent(Bedwars plugin, Result result) {
        super(plugin);
        this.result = result;
    }

    public BedwarsGameEndEvent(Bedwars plugin, Result result, BedwarsTeam team) {
        super(plugin);
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
