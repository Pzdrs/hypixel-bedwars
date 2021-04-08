package me.pycrs.bedwarsrecoded.events;

import me.pycrs.bedwarsrecoded.BPlayer;
import me.pycrs.bedwarsrecoded.BedWars;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class BWPlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private BPlayer player;

    public BWPlayerDeathEvent(BedWars plugin, Player player) {
        this.player = plugin.getBPlayer(player);
        // TODO: 4/8/2021 make this teleport the player to lobby spawn
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(player.getLocation().add(0,100,0));
    }

    public BPlayer getPlayer() {
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
