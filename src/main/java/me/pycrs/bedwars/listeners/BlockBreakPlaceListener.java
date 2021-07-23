package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsBedBreakEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakPlaceListener implements Listener {
    public static Set<Block> placedBlocks = new HashSet<>();
    private Bedwars plugin;

    public BlockBreakPlaceListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        for (Generator generator : plugin.getMap().getDiamondGenerators()) {
            if (Utils.isInArea(event.getBlock().getLocation(), generator.getLocation(), 4, 6, 4)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text("You can't place blocks here!", NamedTextColor.RED));
                return;
            }
        }
        for (Generator generator : plugin.getMap().getEmeraldGenerators()) {
            if (Utils.isInArea(event.getBlock().getLocation(), generator.getLocation(), 4, 6, 4)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text("You can't place blocks here!", NamedTextColor.RED));
                return;
            }
        }
        for (BedwarsTeam team : plugin.getTeams()) {
            if (team.getBaseArea().isInArea(event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        placedBlocks.add(event.getBlock());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!Bedwars.isGameInProgress() || BedwarsPlayer.toBPlayer(event.getPlayer()).isSpectating()) {
            // If a spectator or waiting in lobby - just cancel and say nothing
            event.setCancelled(true);
        } else if (event.getBlock().getBlockData() instanceof Bed) {
            for (BedwarsTeam team : plugin.getTeams())
                if (event.getBlock().getLocation().equals(team.getBedHead()) || event.getBlock().getLocation().equals(team.getBedFoot())) {
                    Bukkit.getServer().getPluginManager().callEvent(new BedwarsBedBreakEvent(team, event));
                    return;
                }
        } else if (!placedBlocks.contains(event.getBlock())) {
            // If just a general block - long message
            event.getPlayer().sendMessage(Component.text("You can only break blocks placed by a player!", NamedTextColor.RED));
            event.setCancelled(true);
        } else placedBlocks.remove(event.getBlock());
    }
}
