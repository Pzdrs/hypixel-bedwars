package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event gets triggered right after the respawn timer hits 0
 */
public class BedwarsPlayerRespawnEvent extends BedwarsPlayerEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsPlayerRespawnEvent(Bedwars plugin, BedwarsPlayer player) {
        super(plugin, player);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
