package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsGameStartEvent extends BedwarsEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsGameStartEvent(Bedwars plugin) {
        super(plugin);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
