package me.pycrs.bedwars.events;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BedwarsBedBreakEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private BedwarsTeam team;
    private BlockBreakEvent event;

    public BedwarsBedBreakEvent(BedwarsTeam team, BlockBreakEvent event) {
        this.team = team;
        this.event = event;
        // The bed does not drop the actual bed item when destroyed
        event.setDropItems(false);
    }

    public BedwarsTeam getTeam() {
        return team;
    }

    public BedwarsPlayer getBedwarsPlayer() {
        return BedwarsPlayer.toBPlayer(event.getPlayer());
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {
        event.setCancelled(cancel);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
