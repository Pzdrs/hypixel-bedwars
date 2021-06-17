package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.BedwarsPlayer;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import me.pycrs.bedwars.events.DeathCause;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private Bedwars plugin;

    public EntityDamageListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player killer = (Player) event.getDamager();
            if (player.getHealth() - event.getFinalDamage() <= 0) {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(
                        new BedwarsPlayerDeathEvent(player, DeathCause.PLAYER_ATTACK, new BedwarsPlayerKillEvent(BedwarsPlayer.toBPlayer(player), BedwarsPlayer.toBPlayer(killer))));
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER) && Bedwars.isGameInProgress()) {
            Player player = (Player) event.getEntity();
            // Custom void instakill
            if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                player.setNoDamageTicks(player.getMaximumNoDamageTicks());
                player.setLastDamage(Double.MAX_VALUE);
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new BedwarsPlayerDeathEvent(player, DeathCause.VOID));
            }
            // "replace" the default PlayerDeathEvent for my custom one
            else if (player.getHealth() - event.getFinalDamage() <= 0) {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new BedwarsPlayerDeathEvent(player, DeathCause.OTHER));
            }
        }
    }
}
