package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import javafx.util.Pair;
import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Utils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.List;

public class MenuUtils {
    public static void displayCategories(Inventory inventory, LinkedList<ShopCategory> categories, ShopCategory selectedCategory) {
        for (int i = 0; i < 9; i++) {
            if (i > categories.size() - 1) {
                inventory.setItem(i + 9, createCategoryDiode());
                continue;
            }
            ShopCategory category = categories.get(i);
            boolean selected = selectedCategory.getId().equals(category.getId());

            inventory.setItem(i, createCategory(category, selected));
            inventory.setItem(i + 9, createCategoryDiode(selected));
        }
    }

    private static ItemStack createCategory(ShopCategory category, boolean selected) {
        return new ItemBuilder(category.getPreview())
                .addLoreLine(selected ? null : "&eClick to view!")
                .build();
    }

    private static ItemStack createCategoryDiode(boolean selected) {
        return new ItemBuilder(selected ? Material.GREEN_STAINED_GLASS_PANE : Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName("&8\u2191 &7Categories")
                .setLore("&8\u2193 &7Items")
                .build();
    }

    private static ItemStack createCategoryDiode() {
        return createCategoryDiode(false);
    }

    public static void addPurchasableItems(Inventory inventory, List<ShopItem> items, Player player) {
        int lastIndex = 18;
        for (int i = 0; i < items.size(); i++) {
            // Rendering more than 21 items on one page isn't possible
            if (i > 20) break;

            ShopItem item = items.get(i);
            ItemStack previewItem = item.getPreview();

            int itemPosition = lastIndex + (i == 7 || i == 14 ? 3 : 1);
            boolean canAfford = canAfford(item.getCurrency(), item.getPrice(), player);
            inventory.setItem(itemPosition, new ItemBuilder(previewItem)
                    .setShopDisplayName(previewItem, canAfford)
                    .addLoreLine(canAfford ? "&eClick to purchase!&r" : "&cYou don't have enough " + BWCurrency.formatName(item.getCurrency()) + "!&r")
                    .build());
            lastIndex = itemPosition;
            System.out.println();
        }
    }

    public static boolean canAfford(BWCurrency currency, int price, Player player) {
        return Utils.getMaterialAmount(player.getInventory(), currency.getType()) >= price;
    }

    public static int canAffordAmount(BWCurrency currency, int price, Player player) {
        int amount = Utils.getMaterialAmount(player.getInventory(), currency.getType());
        if (price > amount) return price - amount;
        return 0;
    }

    public static boolean hasRole(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(BedWars.getInstance(), "role"), PersistentDataType.STRING);
    }

    public static String getItemRole(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "role"), PersistentDataType.STRING);
    }

    public static String getPDCValue(ItemStack itemStack, String key) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), key), PersistentDataType.STRING);
    }

    public static int getCategoryIndex(LinkedList<ShopCategory> categories, ShopCategory category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(category.getId())) return i;
        }
        return -1;
    }

    public static ShopItem getItemById(LinkedList<ShopCategory> categories, String id) {
        for (ShopCategory category : categories) {
            for (ShopItem item : category.getItems()) {
                if (item.getId().equals(id)) return item;
            }
        }
        return null;
    }

    public static ShopItem getItemById(ShopCategory selectedCategory, String id) {
        for (ShopItem item : selectedCategory.getItems())
            if (item.getId().equals(id)) return item;
        return null;
    }
}
