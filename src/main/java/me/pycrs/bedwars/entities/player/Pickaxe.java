package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.menu.shops.items.ShopItem;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public enum Pickaxe {
    NONE(new ItemStack(Material.AIR)),
    WOODEN_PICKAXE(new ItemBuilder(Material.WOODEN_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .setPersistentData(ShopItem.ROLE_KEY, PersistentDataType.STRING, ShopItem.Role.PERSISTENT_EQUIPMENT.key())
            .setUnbreakable(true)
            .build()),
    IRON_PICKAXE(new ItemBuilder(Material.IRON_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 2)
            .setUnbreakable(true)
            .build()),
    GOLD_PICKAXE(new ItemBuilder(Material.GOLDEN_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .addEnchantment(Enchantment.DAMAGE_ALL, 2)
            .setUnbreakable(true)
            .build()),
    DIAMOND_PICKAXE(new ItemBuilder(Material.DIAMOND_PICKAXE)
            .addEnchantment(Enchantment.DIG_SPEED, 3)
            .setUnbreakable(true)
            .build());

    private final ItemStack itemStack;

    Pickaxe(ItemStack itemStack) {
        this.itemStack = itemStack;
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
        return itemStack;
    }
}
