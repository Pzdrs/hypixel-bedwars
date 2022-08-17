package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.util.Utils;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

    private static final InventoryType.SlotType[] FORBIDDEN_SLOTS = new InventoryType.SlotType[]{
            InventoryType.SlotType.CRAFTING,
            InventoryType.SlotType.ARMOR,
            InventoryType.SlotType.FUEL,
            InventoryType.SlotType.RESULT
    };

    public InventoryClickListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onCreativeInventory(InventoryCreativeEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!Utils.atLeastOneEquals(event.getInventory().getType(), new InventoryType[]{
                InventoryType.CHEST, InventoryType.ENDER_CHEST
        })) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;
        // Disable forbidden slots and offhand
        if (Utils.atLeastOneEquals(event.getSlotType(), FORBIDDEN_SLOTS) || event.getSlot() == 40)
            event.setCancelled(true);

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
                if (clickedInventory.getHolder() == player &&
                        event.getInventory().getType() == InventoryType.CHEST ||
                        event.getInventory().getType() == InventoryType.ENDER_CHEST) {
                    if (testItem(event.getCurrentItem())) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            case HOTBAR_SWAP, HOTBAR_MOVE_AND_READD -> {
                if (clickedInventory.getHolder() != player) {
                    if (testItem(event.getClick() == ClickType.NUMBER_KEY ? player.getInventory().getItem(event.getHotbarButton()) : event.getCurrentItem())) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

        if (!event.isCancelled() && event.getAction() != InventoryAction.NOTHING)
            LAST_EVENT.put(player.getUniqueId(), event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (isAToolOrWeapon(event.getOldCursor())) {
            for (Integer rawSlot : event.getRawSlots()) {
                InventoryType.SlotType slotType = event.getView().getSlotType(rawSlot);
                if (slotType == InventoryType.SlotType.QUICKBAR && rawSlot == 45) event.setCancelled(true);
                    // Cancel dragging items into the shield slot
                else if (slotType == InventoryType.SlotType.CONTAINER &&
                        Utils.atLeastOneEquals(event.getInventory().getType(),
                                new InventoryType[]{InventoryType.CHEST, InventoryType.ENDER_CHEST}) &&
                        (rawSlot >= 0 && rawSlot <= 26)) {
                    // Cancel the event if the player tries to drag an item in a CHEST/ENDER_CHEST only
                    event.setCancelled(true);
                } else if (Utils.atLeastOneEquals(event.getView().getSlotType(rawSlot), FORBIDDEN_SLOTS))
                    // Cancel the event in the forbidden slots defined above
                    event.setCancelled(true);
            }
        }
    }

    private boolean isAToolOrWeapon(ItemStack itemStack) {
        return InventoryUtils.hasRole(itemStack, ItemBuilder.ROLE_PERSISTENT_EQUIPMENT);
        //return EnchantmentTarget.TOOL.includes(material) || EnchantmentTarget.WEAPON.includes(material) || material == Material.SHEARS;
    }

    private boolean testItem(ItemStack itemStack) {
        return itemStack != null && isAToolOrWeapon(itemStack);
    }
}
