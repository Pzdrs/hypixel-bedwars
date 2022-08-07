package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.util.InventoryUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BedwarsPlayerKillEvent extends BedwarsPlayerEvent implements BedwarsEventWithMessage{
    private static final HandlerList HANDLERS = new HandlerList();

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

    protected final BedwarsPlayer killer;
    protected final EntityDamageEvent lastDamage;
    protected final Map<Material, Integer> resources;
    protected Component message;


    public BedwarsPlayerKillEvent(Bedwars plugin, BedwarsPlayer player, EntityDamageEvent lastDamage, BedwarsPlayer killer) {
        super(plugin, player);
        this.killer = killer;
        this.lastDamage = lastDamage;
        this.resources = new LinkedHashMap<>();

        for (BWCurrency currency : BWCurrency.values()) {
            int materialAmount = InventoryUtils.getMaterialAmount(player.getPlayer().getInventory(), currency.getType());
            if (materialAmount == 0) continue;
            resources.put(currency.getType(), materialAmount);
        }
    }

    public Map<Material, Integer> getResources() {
        return resources;
    }

    public BedwarsPlayer getBedwarsKiller() {
        return killer;
    }

    public Player getKiller() {
        return killer.getPlayer();
    }

    public boolean isFinalKill() {
        return !bedwarsPlayer.getTeam().hasBed();
    }

    public EntityDamageEvent getLastDamage() {
        return lastDamage;
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
