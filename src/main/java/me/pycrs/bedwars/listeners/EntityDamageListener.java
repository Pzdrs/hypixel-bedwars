package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.BedwarsPlayer;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

public class EntityDamageListener implements Listener {
    public static Map<UUID, Map.Entry<UUID, Integer>> taggedPlayers = new HashMap<>();
    private Bedwars plugin;

    public EntityDamageListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            // Prevent spectators from punching people
            if (BedwarsPlayer.toBPlayer(damager).isSpectating()) {
                event.setCancelled(true);
                return;
            }

            if (taggedPlayers.containsKey(player.getUniqueId())) {
                Bukkit.getScheduler().cancelTask(taggedPlayers.get(player.getUniqueId()).getValue());
            }
            int tid = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    taggedPlayers.remove(player.getUniqueId()), (long) (plugin.getConfig().getDouble("playerTagPeriod") * 20));
            taggedPlayers.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(damager.getUniqueId(), tid));
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER) && Bedwars.isGameInProgress()) {
            Player player = (Player) event.getEntity();
            // Custom void instakill
            if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                player.setNoDamageTicks(player.getMaximumNoDamageTicks());
                player.setLastDamage(Double.MAX_VALUE);
                player.setLastDamageCause(event);
                event.setCancelled(true);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerDeathEvent(player, new ArrayList<>(), 0,
                        player.displayName().append(Component.text(" fell into the void"))));
            } else if (player.getHealth() - event.getFinalDamage() <= 0) {
                // TODO: 6/20/2021 somehow cancel the hurt sound 
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
        if (taggedPlayers.containsKey(event.getEntity().getUniqueId())) {
            Bukkit.getPluginManager().callEvent(new BedwarsPlayerDeathEvent(event.getEntity(),
                    Bukkit.getPlayer(taggedPlayers.get(event.getEntity().getUniqueId()).getKey())));
        } else Bukkit.getPluginManager().callEvent(new BedwarsPlayerDeathEvent(event));
    }
}
