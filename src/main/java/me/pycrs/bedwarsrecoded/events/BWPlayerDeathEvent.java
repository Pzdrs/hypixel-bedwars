package me.pycrs.bedwarsrecoded.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BWPlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public BWPlayerDeathEvent() {
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
