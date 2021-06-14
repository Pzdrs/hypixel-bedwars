package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.Bedwars;
import me.pycrs.bedwarsrecoded.events.BedwarsBedBreakEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsBedBreakListener implements Listener {
    private Bedwars plugin;

    public BedwarsBedBreakListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBedBreak(BedwarsBedBreakEvent event) {
        if (event.getTeam().isPartOfTeam(event.getBedwarsPlayer())) {
            event.getBedwarsPlayer().getPlayer().sendMessage(Component.text("You can't destroy your own bed!", NamedTextColor.RED));
            event.setCancelled(true);
        } else event.getTeam().destroyBed(event.getBedwarsPlayer());
    }
}
