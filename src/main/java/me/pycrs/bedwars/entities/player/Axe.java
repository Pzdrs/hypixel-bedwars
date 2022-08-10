package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Axe implements Equipment {
    NONE(new ItemStack(Material.AIR)),
    WOODEN_AXE(new ItemBuilder(Material.WOODEN_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .addRole(ItemBuilder.ROLE_DEFAULT_EQUIPMENT)
            .build()),
    STONE_AXE(new ItemBuilder(Material.STONE_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .build()),
    IRON_AXE(new ItemBuilder(Material.IRON_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 2)
            .build()),
    DIAMOND_AXE(new ItemBuilder(Material.DIAMOND_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .build());

    private final ItemStack itemStack;

    Axe(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static Axe getDefault() {
        return WOODEN_AXE;
    }

    /**
     * Upgrades the axe by one tier
     *
     * @return upgraded axe
     */
    public Axe getUpgrade() {
        return ordinal() == values().length - 1 ? this : values()[ordinal() + 1];
    }

    /**
     * Downgrades the axe by one tier, the lowest tier is considered to be the wooden axe
     *
     * @return downgraded axe
     */
    public Axe getDowngrade() {
        return ordinal() == 0 || ordinal() == 1 ? this : values()[ordinal() - 1];
    }

    public ItemStack getItemStack() {
        return applyEquipmentMeta(itemStack);
    }
}
