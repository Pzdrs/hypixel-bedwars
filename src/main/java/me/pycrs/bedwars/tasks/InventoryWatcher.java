package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.Sword;
import me.pycrs.bedwars.listeners.InventoryClickListener;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryWatcher extends BukkitRunnable {
    private final Bedwars plugin;

    public InventoryWatcher(Bedwars plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getPlayers().forEach(bedwarsPlayer -> {
            Player player = bedwarsPlayer.getPlayer();
            // If no sword in player's inventory, equip them, otherwise check if there is a default sword, if a default sword
            // is present check for any other sword, if the player has a default sword plus some extra sword(s), remove the default sword
            if (bedwarsPlayer.getEquipment().hasASword()) {
                if (hasDefaultSword(player) && getAmountOfSwords(player) > 1)
                    for (int i = 0; i < player.getInventory().getContents().length; i++) {
                        ItemStack currentItem = player.getInventory().getItem(i);
                        if (currentItem == null) continue;
                        if (Sword.getDefault().getItemStack().getType() == currentItem.getType())
                            player.getInventory().setItem(i, null);
                    }
            } else {
                // Making sure that the player isn't just holding the item with the cursor
                InventoryClickEvent lastEvent = InventoryClickListener.LAST_EVENT.get(player.getUniqueId());
                if (lastEvent != null && lastEvent.getAction() != InventoryAction.PICKUP_ALL)
                    bedwarsPlayer.getEquipment().equip();
            }
        });
    }

    private int getAmountOfSwords(Player player) {
        int swords = 0;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack currentItem = player.getInventory().getItem(i);
            if (currentItem == null) continue;
            if (EnchantmentTarget.WEAPON.includes(currentItem))
                swords++;
        }
        return swords;
    }

    private boolean hasDefaultSword(Player player) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack currentItem = player.getInventory().getItem(i);
            if (currentItem == null) continue;
            if (InventoryUtils.hasRole(currentItem, ItemBuilder.ROLE_DEFAULT_EQUIPMENT))
                return true;
        }
        return false;
    }
}
