package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.Bedwars;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.data.type.Bed;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerInteractListener extends BaseListener<Bedwars> {
    private final Map<UUID, BukkitTask> rightClickHeld = new HashMap<>();
    private final EnumSet<Material> OPEN_INVENTORY_EXTRAS = EnumSet.of(
            Material.CRAFTING_TABLE,
            Material.ENDER_CHEST,
            Material.ANVIL,
            Material.BEACON,
            Material.ENCHANTING_TABLE
    );

    public PlayerInteractListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (Bedwars.isGameInProgress()) {
            // Cancel all spectator interaction
            if (BedwarsPlayer.toBedwarsPlayer(event.getPlayer()).isSpectating()) {
                event.setCancelled(true);
                return;
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
                // Cancel any bed interactions
                if (Tag.BEDS.isTagged(event.getClickedBlock().getType())) {
                    event.setCancelled(true);
                    return;
                } else if (event.getClickedBlock() instanceof Chest) {
                    for (BedwarsTeam team : plugin.getTeams()) {
                        if (team.getTeamChest().equals(event.getClickedBlock().getLocation()) &&
                                !team.isPartOfTeam(event.getPlayer()) && !team.isEliminated()) {
                            event.getPlayer().sendMessage(
                                    Component.text("You cannot open this Chest as the ", NamedTextColor.RED)
                                            .append(team.getTeamColor().getDisplay())
                                            .append(Component.text(" has not been eliminated!", NamedTextColor.RED)));
                            event.setCancelled(true);
                        }
                    }
                }
            }

            // Auto-shield
            if (isRightClick(event) && EnchantmentTarget.WEAPON.includes(event.getMaterial())) {
                if (!rightClickHeld.containsKey(event.getPlayer().getUniqueId())) {
                    if (opensInventory(event.getClickedBlock()) && !event.getPlayer().isSneaking()) return;
                    event.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
                    plugin.getServer().playSound(Sound.sound(org.bukkit.Sound.ITEM_ARMOR_EQUIP_GENERIC, Sound.Source.PLAYER, 1f, 1f));
                    rightClickHeld.put(event.getPlayer().getUniqueId(), Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                        if (!event.getPlayer().isBlocking()) {
                            event.getPlayer().getInventory().setItemInOffHand(null);
                            rightClickHeld.get(event.getPlayer().getUniqueId()).cancel();
                            rightClickHeld.remove(event.getPlayer().getUniqueId());
                        }
                    }, 10, 1));
                }
            }
        }
    }

    private boolean isRightClick(PlayerInteractEvent event) {
        return event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK;
    }

    private boolean opensInventory(Block block) {
        if (block == null) return false;
        return block.getState() instanceof Container || OPEN_INVENTORY_EXTRAS.contains(block.getType());
    }
}
