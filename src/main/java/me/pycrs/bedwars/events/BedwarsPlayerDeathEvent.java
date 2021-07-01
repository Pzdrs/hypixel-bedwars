package me.pycrs.bedwars.events;

import me.pycrs.bedwars.entities.BedwarsPlayer;
import net.kyori.adventure.text.Component;
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

public class BedwarsPlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private BedwarsPlayer player, killer;
    private Component message;

    public BedwarsPlayerDeathEvent(Player player) {
        this.player = BedwarsPlayer.toBPlayer(player);
    }

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
        this.message = createMessage(Objects.requireNonNull(player.getLastDamageCause()))
                .replaceText(TextReplacementConfig.builder().matchLiteral("{player}")
                        .replacement(player.displayName().color(this.player.getTeam().getTeamColor().getColor())).build())
                .replaceText(TextReplacementConfig.builder().matchLiteral("{killer}")
                        .replacement(killer.displayName().color(this.killer.getTeam().getTeamColor().getColor())).build());
        Bukkit.getServer().getPluginManager().callEvent(new BedwarsPlayerKillEvent(BedwarsPlayer.toBPlayer(player), BedwarsPlayer.toBPlayer(killer)));
    }

    private Component createMessage(EntityDamageEvent event) {
        switch (event.getCause()) {
            case PROJECTILE:
                return Component.text("{player} was shot by {killer}.", NamedTextColor.GRAY);
            case VOID:
                return Component.text("{player} was knocked into the void by {killer}.", NamedTextColor.GRAY);
            case FALL:
                return Component.text("{player} hit the ground too hard whilst trying to escape {killer}.", NamedTextColor.GRAY);
            default:
                return Component.text("{player} was killed by {killer}.", NamedTextColor.GRAY);
        }
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
