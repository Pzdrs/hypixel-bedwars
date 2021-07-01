package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.entity.BedwarsPlayer;
import me.pycrs.bedwars.entity.BedwarsTeam;
import me.pycrs.bedwars.Bedwars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private Bedwars plugin;

    public PlayerInteractListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        // Cancel all spectator interaction
        if (BedwarsPlayer.toBPlayer(event.getPlayer()).isSpectating()) {
            event.setCancelled(true);
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getBlockData() instanceof Bed) {
                event.setCancelled(true);
            } else if (event.getClickedBlock().getType() == Material.CHEST) {
                for (BedwarsTeam team : plugin.getTeams()) {
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
}
