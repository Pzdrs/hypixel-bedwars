package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Axe {
    WOODEN_AXE(1, new ItemBuilder(Material.WOODEN_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .setUnbreakable(true)
            .build()),
    STONE_AXE(2, new ItemBuilder(Material.STONE_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .setUnbreakable(true)
            .build()),
    IRON_AXE(3, new ItemBuilder(Material.IRON_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 2)
            .setUnbreakable(true)
            .build()),
    DIAMOND_AXE(4, new ItemBuilder(Material.DIAMOND_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .setUnbreakable(true)
            .build());

    private int tier;
    private ItemStack itemStack;

    Axe(int tier, ItemStack itemStack) {
        this.tier = tier;
        this.itemStack = itemStack;
    }

    public int getTier() {
        return tier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
