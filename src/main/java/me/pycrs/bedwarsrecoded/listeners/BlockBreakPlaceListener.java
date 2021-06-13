package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BPlayer;
import me.pycrs.bedwarsrecoded.Bedwars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.block.Block;
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
        // cant place blocks inside base or near generators or above y256
        placedBlocks.add(event.getBlock());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (plugin.getBPlayer(event.getPlayer()).isSpectating()) {
            // If a spectator - just cancel and say nothing
            event.setCancelled(true);
        } else if (false) {
            // TODO: 6/13/2021 check if inside the base
            // If not near the protected base area - short message
            event.getPlayer().sendMessage(Component.text("You can't break blocks here!", NamedTextColor.RED));
            event.setCancelled(true);
        } else if (!placedBlocks.contains(event.getBlock())) {
            // If just a general block - long message
            event.getPlayer().sendMessage(Component.text("You can only break blocks placed by a player!", NamedTextColor.RED));
            event.setCancelled(true);
        } else placedBlocks.remove(event.getBlock());
    }
}
