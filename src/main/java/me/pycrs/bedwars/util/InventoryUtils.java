package me.pycrs.bedwars.util;

import me.pycrs.bedwars.listeners.InventoryClickListener;
import me.pycrs.bedwars.menu.shops.items.ShopItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;

public class InventoryUtils {
    private InventoryUtils() {
        throw new AssertionError();
    }

    public static <T> Optional<T> getPersistentData(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType<T, T> persistentDataType) {
        if (itemStack.hasItemMeta()) {
            PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
            if (persistentDataContainer.has(namespacedKey, persistentDataType)) {
                return Optional.ofNullable(persistentDataContainer.get(namespacedKey, persistentDataType));
            }
        }
        return Optional.empty();
    }

    public static <T> void getPersistentData(ItemStack itemStack, NamespacedKey namespacedKey, PersistentDataType<T, T> persistentDataType, Consumer<T> consumer) {
        getPersistentData(itemStack, namespacedKey, persistentDataType).ifPresent(consumer);
    }

    public static int getMaterialAmount(Inventory inventory, Material material) {
        int amount = 0;
        for (ItemStack content : inventory.getContents()) {
            if (content != null)
                if (content.getType().equals(material)) amount += content.getAmount();
        }
        return amount;
    }

    public static boolean hasAtLeastOne(Player player, Material... materials) {
        if (player.getGameMode() == GameMode.CREATIVE) return true;
        for (Material material : materials) {
            if (player.getInventory().contains(material)) return true;
        }
        InventoryClickEvent lastEvent = InventoryClickListener.LAST_EVENT.get(player.getUniqueId());
        for (Material material : materials) {
            if (lastEvent != null && !lastEvent.isCancelled() && Utils.atLeastOneEquals(lastEvent.getAction(), new InventoryAction[]{
                    InventoryAction.PICKUP_ALL,
                    InventoryAction.PICKUP_HALF,
                    InventoryAction.PICKUP_ONE,
                    InventoryAction.PICKUP_SOME,
                    InventoryAction.SWAP_WITH_CURSOR
            }) && lastEvent.getCursor().getType() == material)
                return true;
        }
        return false;
    }

    public static String[] getRoles(ItemStack itemStack) {
        Optional<String> potentialRoles = getPersistentData(itemStack, ItemBuilder.ROLES_KEY, PersistentDataType.STRING);
        if (potentialRoles.isEmpty()) return new String[0];
        return potentialRoles.get().split(",");
    }

    public static boolean hasRole(ItemStack itemStack, String role) {
        for (String s : getRoles(itemStack)) {
            if (role.equals(s)) return true;
        }
        return false;
    }

    public static int getAmountOfSwords(Player player) {
        int swords = 0;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack currentItem = player.getInventory().getItem(i);
            if (currentItem == null) continue;
            if (EnchantmentTarget.WEAPON.includes(currentItem))
                swords++;
        }
        return swords;
    }

    public static boolean hasDefaultSword(Player player) {
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack currentItem = player.getInventory().getItem(i);
            if (currentItem == null) continue;
            if (InventoryUtils.hasRole(currentItem, ItemBuilder.ROLE_DEFAULT_EQUIPMENT))
                return true;
        }
        return false;
    }

    public static boolean hasAPickaxe(Player player) {
        return InventoryUtils.hasAtLeastOne(player,
                Material.WOODEN_PICKAXE,
                Material.STONE_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.IRON_PICKAXE,
                Material.DIAMOND_PICKAXE,
                Material.NETHERITE_PICKAXE
        );
    }

    public static boolean hasAnAxe(Player player) {
        return InventoryUtils.hasAtLeastOne(player,
                Material.WOODEN_AXE,
                Material.STONE_AXE,
                Material.GOLDEN_AXE,
                Material.IRON_AXE,
                Material.DIAMOND_AXE,
                Material.NETHERITE_AXE
        );
    }

    public static boolean hasASword(Player player) {
        return InventoryUtils.hasAtLeastOne(player,
                Material.WOODEN_SWORD,
                Material.STONE_SWORD,
                Material.GOLDEN_SWORD,
                Material.IRON_SWORD,
                Material.DIAMOND_SWORD,
                Material.NETHERITE_SWORD
        );
    }
}
