package me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency;

import javafx.util.Pair;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum BWCurrency {
    IRON(Material.IRON_INGOT, NamedTextColor.WHITE),
    GOLD(Material.GOLD_INGOT, NamedTextColor.GOLD),
    DIAMOND(Material.DIAMOND, NamedTextColor.AQUA),
    EMERALD(Material.EMERALD, NamedTextColor.DARK_GREEN);

    private Material material;
    private NamedTextColor color;

    BWCurrency(Material material, NamedTextColor color) {
        this.material = material;
        this.color = color;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public Material getType() {
        return material;
    }

    public static String formatPrice(Pair<BWCurrency, Integer> cost) {
        return ChatColor.valueOf(cost.getKey().getColor().toString().toUpperCase()) + (cost.getValue() + " " + WordUtils.capitalize(cost.getKey().name().toLowerCase())) + ChatColor.RESET;
    }
}
