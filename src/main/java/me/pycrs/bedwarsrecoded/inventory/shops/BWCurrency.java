package me.pycrs.bedwarsrecoded.inventory.shops;

import org.bukkit.Material;

public enum BWCurrency {
    IRON(Material.IRON_INGOT), GOLD(Material.GOLD_INGOT), DIAMOND(Material.DIAMOND), EMERALD(Material.EMERALD);

    private Material material;

    BWCurrency(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
