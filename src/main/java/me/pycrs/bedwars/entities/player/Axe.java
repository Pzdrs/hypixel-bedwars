package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Axe {
    WOODEN_AXE(new ItemBuilder(Material.WOODEN_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .setUnbreakable(true)
            .build()),
    STONE_AXE(new ItemBuilder(Material.STONE_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .setUnbreakable(true)
            .build()),
    IRON_AXE(new ItemBuilder(Material.IRON_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 2)
            .setUnbreakable(true)
            .build()),
    DIAMOND_AXE(new ItemBuilder(Material.DIAMOND_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .setUnbreakable(true)
            .build());

    private final ItemStack itemStack;

    Axe(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
