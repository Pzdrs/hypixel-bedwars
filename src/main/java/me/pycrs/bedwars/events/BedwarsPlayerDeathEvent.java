package me.pycrs.bedwars.events;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class BedwarsPlayerDeathEvent extends Event {
    public enum DeathMessage {
        DEFAULT(null,
                Component.text("{player} was killed by {killer}.", NamedTextColor.GRAY)),
        PROJECTILE(EntityDamageEvent.DamageCause.PROJECTILE,
                Component.text("{player} was shot by {killer}.", NamedTextColor.GRAY)),
        VOID(EntityDamageEvent.DamageCause.VOID,
                Component.text("{player} was knocked into the void by {killer}.", NamedTextColor.GRAY)),
        FALL(EntityDamageEvent.DamageCause.FALL,
                Component.text("{player} hit the ground too hard whilst trying to escape {killer}.", NamedTextColor.GRAY));

        private final EntityDamageEvent.DamageCause deathCause;
        private final Component message;

        DeathMessage(EntityDamageEvent.DamageCause deathCause, Component message) {
            this.deathCause = deathCause;
            this.message = message;
        }

        public static Component getMessage(EntityDamageEvent.DamageCause deathCause, BedwarsPlayer player, BedwarsPlayer killer) {
            Component toReturn = DEFAULT.message;
            for (DeathMessage cause : values())
                if (cause.deathCause == deathCause) {
                    toReturn = cause.message;
                    break;
                }
            return toReturn
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{player}")
                            .replacement(player.getPlayer().displayName().color(player.getTeam().getTeamColor().getTextColor())).build())
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{killer}")
                            .replacement(killer.getPlayer().getPlayer().displayName().color(killer.getTeam().getTeamColor().getTextColor())).build());
        }
    }

    public enum DeathMessageNatural {
        DEFAULT(null,
                Component.text("{player} died.", NamedTextColor.GRAY)),
        VOID(EntityDamageEvent.DamageCause.VOID,
                Component.text("{player} fell into the void.", NamedTextColor.GRAY)),
        FALL(EntityDamageEvent.DamageCause.FALL,
                Component.text("{player} fell from a high place.", NamedTextColor.GRAY)),
        FIRE(EntityDamageEvent.DamageCause.FIRE,
                Component.text("{player} went up in flames.", NamedTextColor.GRAY)),
        DROWNING(EntityDamageEvent.DamageCause.DROWNING,
                Component.text("{player} drowned.", NamedTextColor.GRAY));
        private final EntityDamageEvent.DamageCause deathCause;
        private final Component message;

        DeathMessageNatural(EntityDamageEvent.DamageCause deathCause, Component message) {
            this.deathCause = deathCause;
            this.message = message;
        }

        public static Component getMessage(EntityDamageEvent.DamageCause deathCause, BedwarsPlayer player) {
            Component toReturn = DEFAULT.message;
            for (DeathMessageNatural naturalCause : values())
                if (naturalCause.deathCause == deathCause) {
                    toReturn = naturalCause.message;
                    break;
                }
            return toReturn
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{player}")
                            .replacement(player.getPlayer().displayName().color(player.getTeam().getTeamColor().getTextColor())).build());
        }
    }

    private static final HandlerList HANDLERS = new HandlerList();
    private static final Function<BedwarsPlayer, Component> DEFAULT_DEATH_MESSAGE = bedwarsPlayer ->
            Component.empty()
                    .append(bedwarsPlayer.getPlayer().displayName().color(bedwarsPlayer.getTeam().getTeamColor().getTextColor()))
                    .append(Component.text(" died.", NamedTextColor.GRAY));

    private BedwarsPlayer player, killer;
    private EntityDamageEvent damageEvent;
    private Component message;

    public BedwarsPlayerDeathEvent(Player player) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.damageEvent = player.getLastDamageCause();
        this.message = DEFAULT_DEATH_MESSAGE.apply(this.player);
    }

    public BedwarsPlayerDeathEvent(Player player, Player killer) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.killer = BedwarsPlayer.toBPlayer(killer);
        this.damageEvent = player.getLastDamageCause();
        this.message = DEFAULT_DEATH_MESSAGE.apply(this.player);
        Bukkit.getServer().getPluginManager().callEvent(new BedwarsPlayerKillEvent(BedwarsPlayer.toBPlayer(player), BedwarsPlayer.toBPlayer(killer)));
    }

    public EntityDamageEvent.DamageCause getCause() {
        return damageEvent.getCause();
    }

    public BedwarsPlayer getBedwarsPlayer() {
        return player;
    }

    public Player getPlayer() {
        return player.getPlayer();
    }

    public BedwarsPlayer getBedwarsKiller() {
        return killer;
    }

    public Player getKiller() {
        return killer.getPlayer();
    }

    public void setMessage(Component message) {
        this.message = message;
    }

    public boolean gotKilled() {
        return killer != null;
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
