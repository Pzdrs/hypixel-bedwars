package me.pycrs.bedwars.events;

import me.pycrs.bedwars.BedwarsPlayer;
import me.pycrs.bedwars.Bedwars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BedwarsPlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private BedwarsPlayer player;
    private DeathCause cause;

    public BedwarsPlayerDeathEvent(Player player, DeathCause cause) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.cause = cause;
    }

    public BedwarsPlayerDeathEvent(Player player, DeathCause cause, BedwarsPlayerKillEvent killEvent) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.cause = cause;
        Bukkit.getServer().getPluginManager().callEvent(killEvent);
    }

    public DeathCause getCause() {
        return cause;
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
