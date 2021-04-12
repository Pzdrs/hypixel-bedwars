package me.pycrs.bedwarsrecoded.inventory.shops;

import javafx.util.Pair;
import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopItem {
    private String description;
    private ItemStack preview, product;
    private Pair<BWCurrency, Integer> cost;

    public ShopItem(Material material, int amount, BWCurrency currency, int price, String description) {
        this.description = description;
        this.cost = new Pair<>(currency, price);
        this.preview = formatPreviewItem(material, amount);
        this.product = getFinalProduct(material, amount);
    }

    private ItemStack formatPreviewItem(Material material, int amount) {
        ItemStack preview = new ItemBuilder(material, amount)
                .setPlugin(BedWars.getInstance())
                .setPersistentData("role", PersistentDataType.STRING, "shopItem")
                .build();
        if (description == null) {
            preview.lore(Arrays.asList(
                    Component.text(Utils.color("&7Cost: &r" + BWCurrency.formatPrice(cost))),
                    Component.empty(),
                    Component.text(ChatColor.AQUA + "Sneak Click to remove from Quick Buy")));
        } else {
            List<Component> lore = new ArrayList<>(Arrays.asList(Component.text(Utils.color("&7Cost: &r" + BWCurrency.formatPrice(cost))), Component.empty()));
            for (String s : description.split("\n")) {
                lore.add(Component.text(ChatColor.GRAY + s));
            }
            lore.addAll(Arrays.asList(Component.empty(), Component.text(ChatColor.AQUA + "Sneak Click to remove from Quick Buy")));
            preview.lore(lore);
        }
        return preview;
    }

    public Pair<BWCurrency, Integer> getCost() {
        return cost;
    }

    private ItemStack getFinalProduct(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getPreview() {
        return preview;
    }

    public ItemStack getProduct() {
        return product;
    }

    public BWCurrency getCurrency() {
        return cost.getKey();
    }

    public int getPrice() {
        return cost.getValue();
    }
}
