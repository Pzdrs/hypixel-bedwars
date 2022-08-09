package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.BedwarsItemBuilder;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Sword {
    WOODEN_SWORD(new BedwarsItemBuilder(Material.WOODEN_SWORD)
            .addRoles(BedwarsItemBuilder.ROLE_DEFAULT_EQUIPMENT, BedwarsItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
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
