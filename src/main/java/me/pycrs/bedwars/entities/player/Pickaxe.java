package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Pickaxe {
    WOODEN_PICKAXE(1, new ItemBuilder(Material.WOODEN_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .setUnbreakable(true)
            .build()),
    IRON_PICKAXE(2, new ItemBuilder(Material.IRON_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 2)
            .setUnbreakable(true)
            .build()),
    GOLD_PICKAXE(3, new ItemBuilder(Material.GOLDEN_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .addEnchantment(Enchantment.DAMAGE_ALL, 2)
            .setUnbreakable(true)
            .build()),
    DIAMOND_PICKAXE(4, new ItemBuilder(Material.DIAMOND_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .setUnbreakable(true)
            .build());

    private int tier;
    private ItemStack itemStack;

    Pickaxe(int tier, ItemStack itemStack) {
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
