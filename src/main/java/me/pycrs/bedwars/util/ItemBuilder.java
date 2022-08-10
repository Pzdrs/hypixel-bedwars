package me.pycrs.bedwars.util;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.shops.items.ShopItem;
import me.pycrs.bedwars.menu.shops.items.dependency.ShopItemTier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemBuilder {
    public static final NamespacedKey ROLES_KEY = new NamespacedKey(Bedwars.getInstance(), "roles");
    public static final String ROLE_PERSISTENT_EQUIPMENT = "persistent_equipment";
    public static final String ROLE_DEFAULT_EQUIPMENT = "default_equipment";

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    protected ItemBuilder(ItemStack itemStack, ItemMeta itemMeta) {
        this.itemStack = itemStack;
        this.itemMeta = itemMeta;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (noItemMeta()) return this;
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setArmorColor(Color color) {
        // If the armor item can't be color, i.e. it's not a leather armor piece, do nothing
        if (noItemMeta() || !(itemMeta instanceof LeatherArmorMeta meta)) return this;
        meta.setColor(color);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        if (noItemMeta()) return this;
        itemMeta.addItemFlags(flags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        if (noItemMeta()) return this;
        itemMeta.displayName(Component.text(color(displayName)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDisplayName(Component displayName) {
        if (noItemMeta()) return this;
        itemMeta.displayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeDisplayName() {
        if (noItemMeta()) return this;
        itemMeta.displayName(Component.text(" "));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (noItemMeta()) return this;
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        if (noItemMeta()) return this;
        List<Component> lores = new ArrayList<>();
        for (String string : lore) {
            lores.add(Component.text(color(string)));
        }
        itemMeta.lore(lores);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(Component... lore) {
        if (noItemMeta()) return this;
        itemMeta.lore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        if (line == null || noItemMeta()) return this;
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null)
            lore.add(Component.text(color(line)));
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLoreLine(Component line) {
        if (line == null || noItemMeta()) return this;
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null)
            lore.add(line);
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public <T> ItemBuilder setPersistentData(NamespacedKey namespacedKey, PersistentDataType<T, T> dataType, T value) {
        if (noItemMeta()) return this;
        itemMeta.getPersistentDataContainer().set(namespacedKey, dataType, value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    // BEDWARS SPECIFIC METHODS
    public ItemBuilder setItemDescription(String description, ChatColor color) {
        if (description == null || noItemMeta()) return this;
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

    public ItemBuilder setShopDisplayName(ItemStack itemStack, boolean affordable) {
        if (noItemMeta()) return this;
        Component displayName = (itemStack.hasItemMeta() && itemStack.getItemMeta().displayName() != null ?
                itemStack.getItemMeta().displayName() : Component.text(Utils.materialToFriendlyName(itemStack.getType())));
        itemMeta.displayName(displayName
                .color(affordable ? NamedTextColor.GREEN : NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false));
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

    public ItemBuilder addRoles(String... roles) {
        if (noItemMeta()) return this;
        for (String role : roles) {
            addRole(role);
        }
        return this;
    }

    public ItemBuilder addRole(String role) {
        if (noItemMeta()) return this;
        Optional<String> potentialRoles = InventoryUtils.getPersistentData(itemStack, ROLES_KEY, PersistentDataType.STRING);
        setPersistentData(ROLES_KEY, PersistentDataType.STRING, potentialRoles.isPresent() ? String.format("%s,%s", potentialRoles.get(), role) : role).build();
        return this;
    }

    public ItemStack build() {
        if (!noItemMeta()) itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    protected boolean noItemMeta() {
        return itemMeta == null;
    }
}
