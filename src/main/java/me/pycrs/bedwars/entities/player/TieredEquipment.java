package me.pycrs.bedwars.entities.player;

import org.bukkit.inventory.ItemStack;

public interface TieredEquipment extends Equipment {
    int getTier();

    TieredEquipment getUpgrade();

    TieredEquipment getDowngrade();

    static ItemStack getTier(TieredEquipment[] tiers, int tier, ItemStack defaultTier) {
        if (tier > tiers.length) return defaultTier;
        for (TieredEquipment tieredEquipment : tiers) {
            if (tieredEquipment.getTier() == tier) return tieredEquipment.getItemStack();
        }
        return null;
    }
}
