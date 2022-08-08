package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Armor {
    DEFAULT(
            new ItemBuilder(Material.LEATHER_HELMET)
                    .addEnchantment(Enchantment.WATER_WORKER, 1)
                    .setUnbreakable(true)
                    .build(),
            new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .setUnbreakable(true)
                    .build(),
            new ItemBuilder(Material.LEATHER_LEGGINGS)
                    .setUnbreakable(true)
                    .build(),
            new ItemBuilder(Material.LEATHER_BOOTS)
                    .setUnbreakable(true)
                    .build()
    ),

    CHAIN_ARMOR(
            DEFAULT.helmet,
            DEFAULT.chestplate,
            new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
                    .setUnbreakable(true)
                    .build(),
            new ItemBuilder(Material.CHAINMAIL_BOOTS)
                    .setUnbreakable(true)
                    .build()
    ),

    IRON_ARMOR(
            DEFAULT.helmet,
            DEFAULT.chestplate,
            new ItemBuilder(Material.IRON_LEGGINGS)
                    .setUnbreakable(true)
                    .build(),
            new ItemBuilder(Material.IRON_BOOTS)
                    .setUnbreakable(true)
                    .build()),

    DIAMOND_ARMOR(
            DEFAULT.helmet,
            DEFAULT.chestplate,
            new ItemBuilder(Material.DIAMOND_LEGGINGS)
                    .setUnbreakable(true)
                    .build(),
            new ItemBuilder(Material.DIAMOND_BOOTS)
                    .setUnbreakable(true)
                    .build()
    );

    private final ItemStack helmet, chestplate, leggings, boots;

    Armor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public static Armor getDefault() {
        return DIAMOND_ARMOR;
    }

    /**
     * Returns a tier higher armor, if already the highest tier, itself is returned
     *
     * @return a tier higher armor
     */
    public Armor getUpgrade() {
        return ordinal() == values().length - 1 ? this : values()[ordinal() + 1];
    }

    public Armor getDowngrade() {
        return ordinal() == 0 ? this : values()[ordinal() - 1];
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }
}
