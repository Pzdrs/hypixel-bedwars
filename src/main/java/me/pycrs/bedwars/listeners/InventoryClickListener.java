package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryClickListener extends BaseListener<Bedwars> {
    public static final Map<UUID, InventoryClickEvent> LAST_EVENT = new HashMap<>();

    public InventoryClickListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;
        // Disable any armor/offhand interaction
        if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.getSlot() == 40) event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        switch (event.getAction()) {
            case PLACE_ALL, PLACE_ONE, SWAP_WITH_CURSOR -> {
                if (clickedInventory.getHolder() != player) {
                    if (testItem(event.getCursor())) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            case MOVE_TO_OTHER_INVENTORY -> {
                if (clickedInventory.getHolder() == player) {
                    if (testItem(event.getCurrentItem())) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            case HOTBAR_SWAP, HOTBAR_MOVE_AND_READD -> {
                if (!checkInventoryHolder(clickedInventory, player)) {
                    if (testItem(event.getClick() == ClickType.NUMBER_KEY ? player.getInventory().getItem(event.getHotbarButton()) : event.getCurrentItem())) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
        LAST_EVENT.put(player.getUniqueId(), event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (isAToolOrWeapon(event.getOldCursor())) {
            if (event.getInventory().getHolder() != event.getWhoClicked()) event.setCancelled(true);
        }
    }

    private boolean checkInventoryHolder(Inventory inventory, HumanEntity humanEntity) {
        return inventory.getHolder() == humanEntity;
    }

    private boolean isAToolOrWeapon(ItemStack itemStack) {
        return InventoryUtils.hasRole(itemStack, ItemBuilder.ROLE_PERSISTENT_EQUIPMENT);
        //return EnchantmentTarget.TOOL.includes(material) || EnchantmentTarget.WEAPON.includes(material) || material == Material.SHEARS;
    }

    private boolean testItem(ItemStack itemStack) {
        return itemStack != null && isAToolOrWeapon(itemStack);
    }
}
