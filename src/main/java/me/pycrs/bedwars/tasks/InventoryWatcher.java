package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.listeners.PlayerInteractListener;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryWatcher extends BukkitRunnable {
    private final Bedwars plugin;

    public InventoryWatcher(Bedwars plugin) {
        this.plugin = plugin;
        runTaskTimerAsynchronously(plugin, 0, 1);
    }

    @Override
    public void run() {
        plugin.getPlayers().forEach(bedwarsPlayer -> {
            Player player = bedwarsPlayer.getPlayer();

            // Make sure the player always has the latest armor
            bedwarsPlayer.getEquipment().updateArmor(false);
            bedwarsPlayer.getEquipment().updateEquipment(true);

            // Auto-shield
            if (PlayerInteractListener.PLAYERS_BLOCKING.contains(player) && player.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
                if (!player.isBlocking() || (player.isBlocking() && !EnchantmentTarget.WEAPON.includes(player.getInventory().getItemInMainHand()))) {
                    player.getInventory().setItemInOffHand(null);
                    PlayerInteractListener.PLAYERS_BLOCKING.remove(player);
                }
            }
        });
    }
}
