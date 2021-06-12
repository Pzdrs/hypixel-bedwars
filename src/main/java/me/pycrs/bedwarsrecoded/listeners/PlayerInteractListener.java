package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BTeam;
import me.pycrs.bedwarsrecoded.BedWars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private BedWars plugin;

    public PlayerInteractListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CHEST) {
            for (BTeam team : plugin.getTeams()) {
                if (team.getTeamChest().equals(event.getClickedBlock().getLocation())) {
                    if (!team.isPartOfTeam(event.getPlayer()) && !team.isEliminated()) {
                        event.getPlayer().sendMessage(
                                Component.text("You cannot open this Chest as the ", NamedTextColor.RED)
                                        .append(team.getTeamColor().getDisplay())
                                        .append(Component.text(" has not been eliminated!", NamedTextColor.RED)));
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
