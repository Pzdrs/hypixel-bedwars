package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.BedwarsItemBuilder;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public enum Pickaxe implements Equipment {
    NONE(new ItemStack(Material.AIR)),
    WOODEN_PICKAXE(new BedwarsItemBuilder(Material.WOODEN_PICKAXE)
            .addRoles(BedwarsItemBuilder.ROLE_DEFAULT_EQUIPMENT)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .build()),
    IRON_PICKAXE(new ItemBuilder(Material.IRON_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 2)
            .build()),
    GOLD_PICKAXE(new ItemBuilder(Material.GOLDEN_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .addEnchantment(Enchantment.DAMAGE_ALL, 2)
            .build()),
    DIAMOND_PICKAXE(new ItemBuilder(Material.DIAMOND_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .build());

    private final ItemStack itemStack;

    Pickaxe(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static Pickaxe getDefault() {
        return WOODEN_PICKAXE;
    }

    /**
     * Upgrades the pickaxe by one tier
     *
     * @return upgraded pickaxe
     */
    public Pickaxe getUpgrade() {
        return ordinal() == values().length - 1 ? this : values()[ordinal() + 1];
    }

    /**
     * Downgrades the pickaxe by one tier, the lowest tier is considered to be the wooden pickaxe
     *
     * @return downgraded pickaxe
     */
    public Pickaxe getDowngrade() {
        return ordinal() == 0 || ordinal() == 1 ? this : values()[ordinal() - 1];
    }

    public ItemStack getItemStack() {
        return applyEquipmentMeta(itemStack);
    }
}
