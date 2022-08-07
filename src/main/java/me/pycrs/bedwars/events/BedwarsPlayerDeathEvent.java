package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class BedwarsPlayerDeathEvent extends BedwarsPlayerEvent implements BedwarsEventWithMessage {
    private static final HandlerList HANDLERS = new HandlerList();

    public enum DeathMessage {
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

        DeathMessage(EntityDamageEvent.DamageCause deathCause, Component message) {
            this.deathCause = deathCause;
            this.message = message;
        }

        public static Component getMessage(EntityDamageEvent.DamageCause deathCause, BedwarsPlayer player) {
            Component toReturn = DEFAULT.message;
            for (DeathMessage deathMessage : values())
                if (deathMessage.deathCause == deathCause) {
                    toReturn = deathMessage.message;
                    break;
                }
            return toReturn
                    .replaceText(TextReplacementConfig.builder().matchLiteral("{player}")
                            .replacement(player.getPlayer().displayName().color(player.getTeam().getTeamColor().getTextColor())).build());
        }
    }

    protected final EntityDamageEvent lastDamage;
    protected Component message;

    public BedwarsPlayerDeathEvent(Bedwars plugin, BedwarsPlayer player, EntityDamageEvent lastDamage) {
        super(plugin, player);
        this.lastDamage = lastDamage;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return lastDamage.getCause();
    }

    public void setMessage(Component message) {
        this.message = message;
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
