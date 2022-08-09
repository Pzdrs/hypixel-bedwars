package me.pycrs.bedwars.util;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.shops.items.ShopItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class BedwarsItemBuilder extends ItemBuilder {
    public static final NamespacedKey ROLES_KEY = new NamespacedKey(Bedwars.getInstance(), "roles");
    public static final String ROLE_PERSISTENT_EQUIPMENT = "persistent_equipment";
    public static final String ROLE_DEFAULT_EQUIPMENT = "default_equipment";

    public BedwarsItemBuilder(Material material) {
        super(material);
    }

    public BedwarsItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public BedwarsItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public BedwarsItemBuilder addRoles(String... roles) {
        if (noItemMeta()) return this;
        for (String role : roles) {
            addRole(role);
        }
        return this;
    }

    public BedwarsItemBuilder addRole(String role) {
        if (noItemMeta()) return this;
        Optional<String> potentialRoles = InventoryUtils.getPersistentData(itemStack, ROLES_KEY, PersistentDataType.STRING);
        setPersistentData(ROLES_KEY, PersistentDataType.STRING, potentialRoles.isPresent() ? String.format("%s,%s", potentialRoles.get(), role) : role);
        return this;
    }
}
