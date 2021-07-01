package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entity.BedwarsPlayer;
import me.pycrs.bedwars.generators.Generator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {
    private Bedwars plugin;

    public EntityPickupItemListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!Bedwars.isGameInProgress() || !(event.getEntity() instanceof Player)) return;
        if (Generator.pickupCheck(plugin.getMap(), event)) return;
        Player player = (Player) event.getEntity();
        // Cancel all spectator interaction
        if (BedwarsPlayer.toBPlayer(player).isSpectating()) {
            event.setCancelled(true);
            return;
        }
    }
}
