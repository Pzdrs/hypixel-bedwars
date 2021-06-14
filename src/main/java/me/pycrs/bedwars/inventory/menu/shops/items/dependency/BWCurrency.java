package me.pycrs.bedwars.inventory.menu.shops.items.dependency;

import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

public enum BWCurrency {
    IRON(Material.IRON_INGOT, NamedTextColor.WHITE, false),
    GOLD(Material.GOLD_INGOT, NamedTextColor.GOLD, false),
    DIAMOND(Material.DIAMOND, NamedTextColor.AQUA, true),
    EMERALD(Material.EMERALD, NamedTextColor.DARK_GREEN, true);

    private Material material;
    private NamedTextColor color;
    private boolean plural;

    BWCurrency(Material material, NamedTextColor color, boolean plural) {
        this.material = material;
        this.color = color;
        this.plural = plural;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public Material getType() {
        return material;
    }

    public boolean isPlural() {
        return plural;
    }

    public String capitalize() {
        return WordUtils.capitalize(toString().toLowerCase());
    }
}
