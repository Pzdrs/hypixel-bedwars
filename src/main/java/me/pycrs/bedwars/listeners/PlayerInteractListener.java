package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeamList;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PlayerInteractListener extends BaseListener<Bedwars> {
    public static final Set<Player> PLAYERS_BLOCKING = new HashSet<>();
    private final Map<Player, BukkitRunnable> SHIELD_DELAY = new HashMap<>();
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
        // Cancel all spectator interaction
        if (BedwarsPlayer.isSpectating(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            // Cancel any bed interactions
            if (Tag.BEDS.isTagged(event.getClickedBlock().getType())) {
                event.setCancelled(true);
                return;
            } else if (event.getClickedBlock() instanceof Chest) {
                if (Bedwars.getGameStage().isGameInProgress()) {
                    for (BedwarsTeam team : BedwarsTeamList.getList()) {
                        if (team.getTeamChestLocation().equals(event.getClickedBlock().getLocation()) &&
                                !team.isPartOfTeam(event.getPlayer()) && !team.isEliminated()) {
                            event.getPlayer().sendMessage(
                                    Component.text("You cannot open this Chest as the ", NamedTextColor.RED)
                                            .append(team.getTeamColor().getDisplay())
                                            .append(Component.text(" has not been eliminated!", NamedTextColor.RED)));
                            event.setCancelled(true);
                        }
                    }
                } else event.setCancelled(true);
            }
        }

        // Auto-shield
        if (isRightClick(event) && EnchantmentTarget.WEAPON.includes(event.getMaterial()) && Bedwars.getGameStage().isGameInProgress()) {
            // Don't put up a shield if the player interacted with a block that can open an inventory, needs to be sneaking in that case
            if (opensInventory(event.getClickedBlock()) && !event.getPlayer().isSneaking()) return;
            // Prevent some nasty shenanigans
            if (PLAYERS_BLOCKING.contains(event.getPlayer()) || SHIELD_DELAY.containsKey(event.getPlayer())) return;
            event.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));
            plugin.getServer().playSound(Sound.sound(org.bukkit.Sound.ITEM_ARMOR_EQUIP_GENERIC, Sound.Source.PLAYER, 1f, 1f));
            event.getPlayer().setShieldBlockingDelay(0);
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    PLAYERS_BLOCKING.add(event.getPlayer());
                    SHIELD_DELAY.remove(event.getPlayer());
                }
            };

            SHIELD_DELAY.put(event.getPlayer(), runnable);
            runnable.runTaskLater(plugin, 4);
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
