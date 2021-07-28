package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.MenuUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    private Bedwars plugin;

    public PlayerDropItemListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (MenuUtils.hasRole(event.getItemDrop().getItemStack()) && MenuUtils.getItemRole(event.getItemDrop().getItemStack()).equals("persistent_equipment")) {
            event.setCancelled(true);
        }
    }
}
