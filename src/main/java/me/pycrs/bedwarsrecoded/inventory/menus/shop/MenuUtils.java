package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;

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
}
