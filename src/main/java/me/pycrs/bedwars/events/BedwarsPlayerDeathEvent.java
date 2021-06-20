package me.pycrs.bedwars.events;

import me.pycrs.bedwars.BedwarsPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

public class BedwarsPlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private BedwarsPlayer player, killer;
    private Component message;

    public BedwarsPlayerDeathEvent(PlayerDeathEvent event) {
        this.player = BedwarsPlayer.toBPlayer(event.getEntity());
        this.message = Component.empty()
                .append(event.getEntity().displayName().color(player.getTeam().getTeamColor().getColor()))
                .append(Objects.requireNonNull(event.deathMessage())
                        .replaceText(TextReplacementConfig.builder().matchLiteral(event.getEntity().getName())
                                .replacement(Component.empty()).build())
                        .color(NamedTextColor.GRAY))
                .append(Component.text(".", NamedTextColor.GRAY));
    }

    public BedwarsPlayerDeathEvent(Player player, Player killer) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.killer = BedwarsPlayer.toBPlayer(killer);
        this.message = Component.text("{player} was killed by {killer}.", NamedTextColor.GRAY)
                .replaceText(TextReplacementConfig.builder().matchLiteral("{player}")
                        .replacement(player.displayName().color(this.player.getTeam().getTeamColor().getColor())).build())
                .replaceText(TextReplacementConfig.builder().matchLiteral("{killer}")
                        .replacement(killer.displayName().color(this.killer.getTeam().getTeamColor().getColor())).build());
        Bukkit.getServer().getPluginManager().callEvent(new BedwarsPlayerKillEvent(BedwarsPlayer.toBPlayer(player), BedwarsPlayer.toBPlayer(killer)));
    }

    public BedwarsPlayer getBPlayer() {
        return player;
    }

    public Component getMessage() {
        return message;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
