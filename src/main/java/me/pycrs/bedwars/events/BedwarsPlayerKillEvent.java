package me.pycrs.bedwars.events;

import me.pycrs.bedwars.BedwarsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsPlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private BedwarsPlayer player, killer;

    public BedwarsPlayerKillEvent(BedwarsPlayer player, BedwarsPlayer killer) {
        this.player = player;
        this.killer = killer;
    }

    public BedwarsPlayer getPlayer() {
        return player;
    }

    public BedwarsPlayer getKiller() {
        return killer;
    }

    public boolean isFinalKill() {
        return !player.getTeam().hasBed();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
