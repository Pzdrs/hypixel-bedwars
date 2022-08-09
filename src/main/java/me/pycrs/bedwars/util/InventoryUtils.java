package me.pycrs.bedwars.util;

import me.pycrs.bedwars.menu.shops.items.ShopItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
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
        for (Material material : materials) {
            if (player.getInventory().contains(material)) return true;
        }
        return false;
    }

    public static String[] getRoles(ItemStack itemStack) {
        Optional<String> potentialRoles = getPersistentData(itemStack, BedwarsItemBuilder.ROLES_KEY, PersistentDataType.STRING);
        if (potentialRoles.isEmpty()) return new String[0];
        return potentialRoles.get().split(",");
    }

    public static boolean hasRole(ItemStack itemStack, String role) {
        for (String s : getRoles(itemStack)) {
            if (role.equals(s)) return true;
        }
        return false;
    }
}
