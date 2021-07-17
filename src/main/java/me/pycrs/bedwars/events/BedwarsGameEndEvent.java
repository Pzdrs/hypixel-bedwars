package me.pycrs.bedwars.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsGameEndEvent extends Event {
    public enum Result {
        EVERYONE_LEFT
    }

    private Result result;

    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsGameEndEvent(Result result) {
        this.result = result;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
