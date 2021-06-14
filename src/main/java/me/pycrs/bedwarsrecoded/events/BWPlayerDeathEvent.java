package me.pycrs.bedwarsrecoded.events;

import me.pycrs.bedwarsrecoded.BedwarsPlayer;
import me.pycrs.bedwarsrecoded.Bedwars;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BWPlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private BedwarsPlayer player;

    public BWPlayerDeathEvent(Bedwars plugin, Player player) {
        this.player = BedwarsPlayer.toBPlayer(player);
    }

    public BedwarsPlayer getBPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
