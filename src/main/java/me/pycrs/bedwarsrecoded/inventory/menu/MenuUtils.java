package me.pycrs.bedwarsrecoded.inventory.menu;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Utils;
import me.pycrs.bedwarsrecoded.inventory.menu.button.MenuButton;
import me.pycrs.bedwarsrecoded.inventory.menu.button.MenuButtonHandler;
import me.pycrs.bedwarsrecoded.inventory.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menu.shops.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menu.shops.items.ShopItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class MenuUtils {
    public static void displayCategories(Inventory inventory, LinkedList<ShopCategory> categories, ShopCategory selectedCategory) {
        for (int i = 0; i < 9; i++) {
            if (i > categories.size() - 1) {
                inventory.setItem(i + 9, createCategoryDiode());
                continue;
            }
            ShopCategory category = categories.get(i);
            boolean selected = selectedCategory.getId().equals(category.getId());

            inventory.setItem(i, createCategory(category.getPreview().clone(), selected));
            inventory.setItem(i + 9, createCategoryDiode(selected));
        }
    }

    private static ItemStack createCategory(ItemStack preview, boolean selected) {
        return new ItemBuilder(preview)
                .addLoreLine(selected ? null : "&eClick to view!")
                .build();
    }

    private static ItemStack createCategoryDiode(boolean selected) {
        return new ItemBuilder(selected ? Material.GREEN_STAINED_GLASS_PANE : Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName("&8\u2191 &7Categories")
                .setLore("&8\u2193 &7Items")
                .build();
    }

    public static void fillRow(int row, Inventory inventory, ItemStack itemStack) {
        if (row * 9 > inventory.getSize()) return;
        for (int i = (row - 1) * 9; i < row * 9; i++) {
            inventory.setItem(i, itemStack);
        }
    }

    private static ItemStack createCategoryDiode() {
        return createCategoryDiode(false);
    }

    public static void addPurchasableItems(int start, Inventory inventory, List<ShopItem> items, Player player) {
        int lastIndex = start;
        for (int i = 0; i < items.size(); i++) {
            // Rendering more than 21 items on one page isn't possible
            if (i > 20) break;

            ShopItem item = items.get(i);
            ItemStack previewItem = item.getPreview().clone();

            int itemPosition = lastIndex + (i == 7 || i == 14 ? 3 : 1);
            boolean canAfford = canAfford(item.getCost().getCurrency(), item.getCost().getPrice(), player);
            inventory.setItem(itemPosition, new ItemBuilder(previewItem)
                    .setShopDisplayName(previewItem, canAfford)
                    .addLoreLine(canAfford ? "&eClick to purchase!&r" : "&cYou don't have enough " + item.getCost().getCurrency().capitalize() + "!&r")
                    .build());
            lastIndex = itemPosition;
        }
    }

    public static void addPurchasableItems(Inventory inventory, List<ShopItem> items, Player player) {
        addPurchasableItems(18, inventory, items, player);
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

    public static ShopCategory getCategoryById(LinkedList<ShopCategory> categories, String id) {
        for (ShopCategory category : categories) {
            if (category.getId().equals(id)) return category;
        }
        return null;
    }

    // TODO: 4/25/2021 somehow run the logic when player clicks this item
    public static void createButton(ItemStack itemStack, Menu menu, int slot, MenuButtonHandler handler) {
        String id = UUID.randomUUID().toString();
        menu.getInventory().setItem(slot, new ItemBuilder(itemStack)
                .setPlugin(BedWars.getInstance())
                .setPersistentData("role", PersistentDataType.STRING, "menuButton")
                .setPersistentData("menuButtonID", PersistentDataType.STRING, id)
                .build());
        menu.getButtons().put(id, new MenuButton(itemStack, handler));
    }
}
