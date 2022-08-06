package me.pycrs.bedwars.events;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.util.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BedwarsPlayerKillEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private BedwarsPlayer player, killer;

    private Map<Material, Integer> resources;


    public BedwarsPlayerKillEvent(Player player, Player killer) {
        this.player = BedwarsPlayer.toBPlayer(player);
        this.killer = BedwarsPlayer.toBPlayer(killer);
        this.resources = new LinkedHashMap<>();

        for (BWCurrency currency : BWCurrency.values()) {
            int materialAmount = InventoryUtils.getMaterialAmount(player.getInventory(), currency.getType());
            if (materialAmount == 0) continue;
            resources.put(currency.getType(), materialAmount);
        }
    }

    public Map<Material, Integer> getResources() {
        return resources;
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
