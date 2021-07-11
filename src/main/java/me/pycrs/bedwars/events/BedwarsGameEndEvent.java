package me.pycrs.bedwars.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsGameEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsGameEndEvent() {
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
