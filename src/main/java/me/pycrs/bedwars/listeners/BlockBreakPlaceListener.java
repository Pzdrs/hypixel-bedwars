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
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakPlaceListener extends BaseListener<Bedwars> {
    private static final Set<Block> PLACED_BLOCKS = new HashSet<>();

    public BlockBreakPlaceListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!Bedwars.getGameStage().isGameInProgress()) event.setCancelled(true);
        for (Generator generator : Bedwars.getMap().getDiamondGenerators()) {
            if (Utils.isInArea(event.getBlock().getLocation(), generator.getLocation(), 4, 6, 4)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text("You can't place blocks here!", NamedTextColor.RED));
                return;
            }
        }
        for (Generator generator : Bedwars.getMap().getEmeraldGenerators()) {
            if (Utils.isInArea(event.getBlock().getLocation(), generator.getLocation(), 4, 6, 4)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text("You can't place blocks here!", NamedTextColor.RED));
                return;
            }
        }
        for (BedwarsTeam team : plugin.getTeams()) {
            if (team.getBaseArea().isInArea(event.getBlock().getLocation())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Component.text("You can't place blocks here!", NamedTextColor.RED));
                return;
            }
        }
        PLACED_BLOCKS.add(event.getBlock());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!Bedwars.getGameStage().isGameInProgress() || BedwarsPlayer.isSpectating(event.getPlayer())) {
            // If a spectator or waiting in lobby - just cancel and say nothing
            event.setCancelled(true);
        } else if (event.getBlock().getBlockData() instanceof Bed) {
            for (BedwarsTeam team : plugin.getTeams())
                if (event.getBlock().getLocation().equals(team.getBedHeadLocation()) || event.getBlock().getLocation().equals(team.getBedFootLocation())) {
                    Bukkit.getServer().getPluginManager().callEvent(new BedwarsBedBreakEvent(plugin, team, event));
                    return;
                }
        } else if (!PLACED_BLOCKS.contains(event.getBlock())) {
            // If just a general block - long message
            event.getPlayer().sendMessage(Component.text("You can only break blocks placed by a player!", NamedTextColor.RED));
            event.setCancelled(true);
        } else PLACED_BLOCKS.remove(event.getBlock());
    }

    @EventHandler
    public void onBreakHanging(HangingBreakEvent event) {
        event.setCancelled(true);
    }
}
