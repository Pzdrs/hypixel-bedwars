package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.*;

public class EntityDamageListener implements Listener {
    private static Map<UUID, Map.Entry<UUID, Integer>> taggedPlayers = new HashMap<>();
    private Bedwars plugin;

    public EntityDamageListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && (event.getDamager() instanceof Player || event.getDamager() instanceof Projectile)) {
            Player damager;
            if (event.getDamager() instanceof Player)
                damager = (Player) event.getDamager();
            else
                damager = (Player) ((Projectile) event.getDamager()).getShooter();

            // Prevent spectators from punching people
            if (BedwarsPlayer.toBPlayer(damager).isSpectating()) {
                event.setCancelled(true);
                return;
            }

            if (taggedPlayers.containsKey(player.getUniqueId())) {
                Bukkit.getScheduler().cancelTask(taggedPlayers.get(player.getUniqueId()).getValue());
            }
            int tid = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    taggedPlayers.remove(player.getUniqueId()), Settings.playerTagPeriod * 20L);
            taggedPlayers.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(damager.getUniqueId(), tid));
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER) && Bedwars.isGameInProgress()) {
            Player player = (Player) event.getEntity();

            // If a player receives a lethal amount of damage, cancel and manually kill, so it matches the Hypixel behaviour
            if (event.getFinalDamage() >= player.getHealth()) {
                event.setCancelled(true);
                player.setLastDamageCause(event);
                player.setFireTicks(0);
                // The tiniest delay before actually killing the player so there is enough time for the sound to play
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> killPlayer(BedwarsPlayer.toBPlayer(player)), 1);
                // To avoid double death, i.e. using /kill
                return;
            }

            // Custom void instakill
            if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                player.setNoDamageTicks(player.getMaximumNoDamageTicks());
                player.setLastDamage(Double.MAX_VALUE);
                player.setLastDamageCause(event);
                event.setCancelled(true);
                killPlayer(BedwarsPlayer.toBPlayer(player));
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemHeld(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    private void killPlayer(BedwarsPlayer player) {
        if (taggedPlayers.containsKey(player.getPlayer().getUniqueId())) {
            player.kill(Bukkit.getPlayer(taggedPlayers.get(player.getPlayer().getUniqueId()).getKey()));
        } else player.kill();
    }
}
