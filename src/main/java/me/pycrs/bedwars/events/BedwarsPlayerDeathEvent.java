package me.pycrs.bedwars.events;

import me.pycrs.bedwars.BedwarsPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BedwarsPlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private BedwarsPlayer player;
    private DeathCause cause;
    private Component message;

    public BedwarsPlayerDeathEvent(Player player, DeathCause cause) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.cause = cause;
        this.message = createMessage(cause, null);
    }

    public BedwarsPlayerDeathEvent(Player player, DeathCause cause, BedwarsPlayerKillEvent killEvent) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.cause = cause;
        this.message = createMessage(cause, killEvent);
        Bukkit.getServer().getPluginManager().callEvent(killEvent);
    }

    public DeathCause getCause() {
        return cause;
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

    private Component createMessage(DeathCause cause, BedwarsPlayerKillEvent killEvent) {
        switch (cause) {
            case PLAYER_ATTACK:
                BedwarsPlayer player = killEvent.getPlayer();
                BedwarsPlayer killer = killEvent.getKiller();
                return player.getPlayer().displayName().color(player.getTeam().getTeamColor().getColor())
                        .append(cause.getRandomMessage())
                        .append(killer.getPlayer().displayName().color(killer.getTeam().getTeamColor().getColor()));
            case VOID:
            case FALL:
            default:
                return this.player.getPlayer().displayName().color(this.player.getTeam().getTeamColor().getColor()).append(cause.getRandomMessage());
        }
    }

    public enum DeathCause {
        OTHER(new Component[]{
                Component.text(" died.", NamedTextColor.GRAY)
        }),
        VOID(new Component[]{
                Component.text(" fell into the void.", NamedTextColor.GRAY)}),
        FALL(new Component[]{
                Component.text(" impacted the ground at high velocity.", NamedTextColor.GRAY)}),
        PLAYER_ATTACK(new Component[]{
                Component.text(" was killed by ", NamedTextColor.GRAY)
        });

        private Component[] messages;

        DeathCause(Component[] messages) {
            this.messages = messages;
        }

        public Component getRandomMessage() {
            return messages[new Random().nextInt(messages.length)];
        }
    }
}
