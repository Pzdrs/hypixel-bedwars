package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BedwarsBedBreakEvent extends BedwarsTeamEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    protected boolean cancelled = false;
    private final BlockBreakEvent event;

    public BedwarsBedBreakEvent(Bedwars plugin, BedwarsTeam team, BlockBreakEvent event) {
        super(plugin, team);
        this.event = event;
        // The bed does not drop the actual bed item when destroyed
        event.setDropItems(false);
    }

    public BedwarsPlayer getBedwarsPlayer() {
        return BedwarsPlayer.toBedwarsPlayer(event.getPlayer());
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
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
