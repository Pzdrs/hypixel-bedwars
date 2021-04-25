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

    public static String formatName(BWCurrency currency) {
        return WordUtils.capitalize(currency.name().toLowerCase());
    }

    public static String formatPrice(BWCurrency currency, int price) {
        return ChatColor.valueOf(currency.getColor().toString().toUpperCase()) + (price + " " + WordUtils.capitalize(currency.name().toLowerCase() +
                ((currency.equals(DIAMOND) || currency.equals(EMERALD)) && price != 1 ? "s" : ""))) + ChatColor.RESET;
    }
}
