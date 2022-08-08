package me.pycrs.bedwars.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

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

    public static int getMaterialAmount(Inventory inventory, Material material) {
        int amount = 0;
        for (ItemStack content : inventory.getContents()) {
            if (content != null)
                if (content.getType().equals(material)) amount += content.getAmount();
        }
        return amount;
    }
}
