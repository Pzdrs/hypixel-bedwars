package me.pycrs.bedwarsrecoded;

import me.pycrs.bedwarsrecoded.inventory.menu.shop.item.dependency.ShopItemTier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private JavaPlugin plugin;
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        setMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        setMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        setMeta();
    }

    public ItemBuilder setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.displayName(Component.text(color(displayName)));
        return this;
    }

    public ItemBuilder setDisplayName(Component displayName) {
        itemMeta.displayName(displayName);
        return this;
    }

    public ItemBuilder setShopDisplayName(ItemStack itemStack, boolean affordable) {
        Component displayName = (itemStack.hasItemMeta() && itemStack.getItemMeta().displayName() != null ?
                itemStack.getItemMeta().displayName() : Component.text(Utils.materialToFriendlyName(itemStack.getType())));
        itemMeta.displayName(displayName
                .color(affordable ? NamedTextColor.GREEN : NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false));
        return this;
    }

    public ItemBuilder removeDisplayName() {
        itemMeta.displayName(Component.text(" "));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        List<Component> lores = new ArrayList<>();
        for (String string : lore) {
            lores.add(Component.text(color(string)));
        }
        itemMeta.lore(lores);
        return this;
    }

    public ItemBuilder setLore(Component... lore) {
        itemMeta.lore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        if (line == null) return this;
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null)
            lore.add(Component.text(color(line)));
        itemMeta.lore(lore);
        return this;
    }

    public ItemBuilder addLoreLine(Component line) {
        if (line == null) return this;
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null)
            lore.add(line);
        itemMeta.lore(lore);
        return this;
    }

    public ItemBuilder setItemDescription(String description, ChatColor color) {
        if (description == null) return this;
        List<Component> lore = itemMeta.hasLore() ? itemMeta.lore() : new ArrayList<>();
        if (lore != null) {
            for (String s : description.split("\n"))
                lore.add(Component.text(color + s));
            lore.add(Component.empty());
        }
        itemMeta.lore(lore);
        return this;
    }


    public ItemBuilder setShopItemTiers(Map<ShopItemTier, Boolean> tiers) {
        System.out.println(tiers);
        int tierIndex = 1;
        for (Map.Entry<ShopItemTier, Boolean> tier : tiers.entrySet()) {
            addLoreLine(Component.text("Tier ")
                    .append(Component.text(tierIndex + ": "))
                    .append(Component.text(tier.getKey().getDescription())));
        }
        return this;
    }

    public ItemBuilder previewEnchantments() {
        if (!itemMeta.hasEnchants()) return this;
        itemMeta.getEnchants().forEach((enchantment, integer) -> {
            itemMeta.displayName().append(enchantment.displayName(integer));
        });
        /*setDisplayName(itemMeta.displayName() == null ? Component.text(Utils.materialToFriendlyName(itemStack.getType())).append(enchantsPreview) :
                itemMeta.displayName().append(enchantsPreview));*/
        return this;
    }

    public ItemBuilder setPersistentData(String key, PersistentDataType dataType, Object value) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), dataType, value);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    private void setMeta() {
        this.itemMeta = this.itemStack.getItemMeta();
    }
}
