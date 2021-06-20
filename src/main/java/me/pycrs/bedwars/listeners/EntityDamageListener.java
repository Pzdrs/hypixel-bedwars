package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.BedwarsPlayer;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
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
            Player killer = (Player) event.getDamager();
            if (taggedPlayers.containsKey(player.getUniqueId()))
                Bukkit.getScheduler().cancelTask(taggedPlayers.get(player.getUniqueId()).getValue());
            int tid = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    taggedPlayers.remove(player.getUniqueId()), (long) (plugin.getConfig().getDouble("playerTagPeriod") * 20));
            taggedPlayers.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(killer.getUniqueId(), tid));
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
                event.setCancelled(true);

                Bukkit.getServer().getPluginManager().callEvent(new PlayerDeathEvent(player, new ArrayList<>(), 0,
                        player.displayName().append(Component.text(" fell into the void."))));
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
