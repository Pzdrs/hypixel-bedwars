package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.BedwarsPlayer;
import me.pycrs.bedwars.BedwarsTeam;
import me.pycrs.bedwars.generators.Generator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EntityPickupItemListener implements Listener {
    private Bedwars plugin;

    public EntityPickupItemListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!Bedwars.isGameInProgress() || !(event.getEntity() instanceof Player)) return;
        if (Generator.pickupCheck(plugin.getMap(), event))return;
        Player player = (Player) event.getEntity();
        // Cancel all spectator interaction
        if (BedwarsPlayer.toBPlayer(player).isSpectating()) {
            event.setCancelled(true);
            return;
        }
    }
}
