package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.listeners.PlayerInteractListener;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;

public class InventoryWatcher extends BedwarsRunnable {
    private static InventoryWatcher INSTANCE;

    public static void start(Bedwars plugin) {
        INSTANCE = new InventoryWatcher(plugin);
        INSTANCE.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    public static void stop() {
        INSTANCE.cancel();
    }

    public InventoryWatcher(Bedwars plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        BedwarsPlayer.all().nonSpectators().forEach(bedwarsPlayer -> {
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
