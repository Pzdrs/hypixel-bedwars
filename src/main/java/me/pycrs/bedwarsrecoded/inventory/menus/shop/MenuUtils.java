package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import javafx.util.Pair;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Utils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopItem;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
            ItemStack itemStack = item.getPreview();
            int itemPosition = lastIndex + (i == 7 || i == 14 ? 3 : 1);
            inventory.setItem(itemPosition, new ItemBuilder(itemStack)
                    .setDisplayName((canAfford(item.getCost(),player) ? ChatColor.GREEN : ChatColor.RED) + Utils.materialToFriendlyName(itemStack.getType()))
                    .addLoreLine(canAfford(item.getCost(), player) ? "&eClick to purchase!" : "&cYou don't have enough " + WordUtils.capitalize(item.getCost().getKey().name().toLowerCase()) + "!")
                    .build());
            lastIndex = itemPosition;
        }
    }

    private static boolean canAfford(Pair<BWCurrency, Integer> cost, Player player) {
        return Utils.getMaterialAmount(player.getInventory(), cost.getKey().getType()) >= cost.getValue();
    }
}
