package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.events.BWPlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private BedWars plugin;

    public EntityDamageListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER) && BedWars.gameInProgress) {
            Player player = (Player) event.getEntity();
            if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) Bukkit.getPluginManager().callEvent(new BWPlayerDeathEvent(plugin, player));
        }
    }
}
