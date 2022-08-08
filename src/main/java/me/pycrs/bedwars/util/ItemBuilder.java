package me.pycrs.bedwars.util;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.shops.items.dependency.ShopItemTier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.type.Bed;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setArmorColor(Color color) {
        // If the armor item can't be color, i.e. it's not a leather armor piece, do nothing
        if (!(itemStack.getItemMeta() instanceof LeatherArmorMeta meta)) return this;
        meta.setColor(color);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(flags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(color(displayName)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDisplayName(Component displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setShopDisplayName(ItemStack itemStack, boolean affordable) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Component displayName = (itemStack.hasItemMeta() && itemStack.getItemMeta().displayName() != null ?
                itemStack.getItemMeta().displayName() : Component.text(Utils.materialToFriendlyName(itemStack.getType())));
        itemMeta.displayName(displayName
                .color(affordable ? NamedTextColor.GREEN : NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeDisplayName() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(" "));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<Component> lores = new ArrayList<>();
        for (String string : lore) {
            lores.add(Component.text(color(string)));
        }
        itemMeta.lore(lores);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(Component... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        if (line == null) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null)
            lore.add(Component.text(color(line)));
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLoreLine(Component line) {
        if (line == null) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null)
            lore.add(line);
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setItemDescription(String description, ChatColor color) {
        if (description == null) return this;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null) {
            for (String s : description.split("\n"))
                lore.add(Component.text(color + s));
            lore.add(Component.empty());
        }
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemBuilder setShopItemTiers(Map<ShopItemTier, Boolean> tiers) {
        int tierIndex = 1;
        for (Map.Entry<ShopItemTier, Boolean> tier : tiers.entrySet()) {
            addLoreLine(Component.text("Tier ")
                    .append(Component.text(tierIndex + ": "))
                    .append(Component.text(tier.getKey().getDescription())));
        }
        return this;
    }

    public ItemBuilder previewEnchantments() {
       /* if (!itemMeta.hasEnchants() || !itemMeta.hasDisplayName()) return this;
        itemMeta.getEnchants().forEach((enchantment, integer) -> {
            itemMeta.displayName().append(...);
        });*/
        /*setDisplayName(itemMeta.displayName() == null ? Component.text(Utils.materialToFriendlyName(itemStack.getType())).append(enchantsPreview) :
                itemMeta.displayName().append(enchantsPreview));*/
        return this;
    }

    public <T> ItemBuilder setPersistentData(NamespacedKey namespacedKey, PersistentDataType<T, T> dataType, T value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, dataType, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    private String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
