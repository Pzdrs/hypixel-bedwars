package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.menu.shops.items.ShopItem;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public enum Axe {
    NONE(new ItemStack(Material.AIR)),
    WOODEN_AXE(new ItemBuilder(Material.WOODEN_AXE)
            .addEnchantment(Enchantment.DIG_SPEED, 1)
            .setPersistentData(ShopItem.ROLE_KEY, PersistentDataType.STRING, ShopItem.Role.PERSISTENT_EQUIPMENT.key())
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
        return itemStack;
    }
}
