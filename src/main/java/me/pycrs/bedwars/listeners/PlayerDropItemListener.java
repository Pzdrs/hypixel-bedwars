package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.menu.shops.items.ShopItem;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.MenuUtils;
import net.minecraft.server.v1_16_R3.BehaviorBedJump;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class PlayerDropItemListener extends BaseListener<Bedwars> {
    public PlayerDropItemListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!Bedwars.isGameInProgress() || Bedwars.isGameFinished()) {
            event.setCancelled(true);
            return;
        }
        ItemStack itemStack = event.getItemDrop().getItemStack();

        if (InventoryUtils.hasRole(itemStack, "persistent_equipment")) event.setCancelled(true);
    }
}
