package me.pycrs.bedwars.entities.player.equipment.types;

import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Sword {
    WOODEN_SWORD(new ItemBuilder(Material.WOODEN_SWORD)
            .addRoles(ItemBuilder.ROLE_DEFAULT_EQUIPMENT, ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
            .build()
    ),
    STONE_SWORD(new ItemBuilder(Material.STONE_SWORD)
            .setUnbreakable(true)
            .build()
    ),
    IRON_SWORD(new ItemBuilder(Material.IRON_SWORD)
            .setUnbreakable(true)
            .build()
    ),
    DIAMOND_SWORD(new ItemBuilder(Material.DIAMOND_SWORD)
            .setUnbreakable(true)
            .build()
    );

    private final ItemStack itemStack;

    Sword(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static Sword getDefault() {
        return WOODEN_SWORD;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
