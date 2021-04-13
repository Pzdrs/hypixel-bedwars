package me.pycrs.bedwarsrecoded.inventory.shops;

import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Shop implements InventoryHolder {
    private Inventory inventory;
    protected LinkedList<ShopCategory> categories;
    protected ShopCategory activeCategory;
    private Player player;
    protected List<ShopItem> items;

    public Shop(Player player) {
        this.player = player;
        this.items = new ArrayList<>();
        this.categories = new LinkedList<>();
    }

    public abstract void handlePurchase(InventoryClickEvent event);

    public abstract boolean categorical();

    public abstract int getSize();

    public void setShopItems() {
    }

    public void setCategories() {
    }

    public void setActiveCategory(String id) {
        this.activeCategory = categories
                .stream()
                .filter(category -> category.getId().equals(id))
                .findFirst().orElse(null);
    }

    private void setupInventory() {
        this.inventory = Bukkit.createInventory(this, getSize(), Component.text(activeCategory.getName()));
        injectItems();
    }

    public final void show() {
        items.clear();
        categories.clear();
        setShopItems();
        setCategories();
        if (activeCategory == null)this.activeCategory = categories.get(0);

        setupInventory();
        player.openInventory(inventory);
    }

    // FIXME: 4/13/2021 players swinging his hand when navigation to a different category
    public void navigate(String category) {
        setActiveCategory(category);
        show();
    }

    private void injectItems() {
        if (categorical()) {
            inventory.setItem(9, Utils.getCategoryDiode(false));
            inventory.setItem(17, Utils.getCategoryDiode(false));
            for (int i = 0; i < categories.size(); i++) {
                ShopCategory category = categories.get(i);
                boolean active = activeCategory.getId().equals(category.getId());

                inventory.setItem(i, active ? category.getPreview() : new ItemBuilder(category.getPreview()).addLoreLine("&eClick to view!").build());
                inventory.setItem(i + 9, Utils.getCategoryDiode(active));
                if (active) {
                    int lastItemPosition = 18;
                    for (int j = 0; j < category.getItems().size(); j++) {
                        // Rendering more than 21 items on one page isn't possible
                        if (j > 20) break;

                        ShopItem item = category.getItems().get(j);
                        ItemStack itemStack = item.getPreview();
                        ItemMeta meta = itemStack.getItemMeta();

                        meta.displayName(Component.text((Utils.canAfford(player, item.getCost()) ? ChatColor.GREEN : ChatColor.RED) + Utils.materialToFriendlyName(itemStack.getType())));

                        List<Component> lore = new ArrayList<>(Objects.requireNonNull(meta.lore()));
                        lore.add(Component.text(Utils.color(Utils.canAfford(player, item.getCost()) ? "&eClick to purchase!" : "&cYou don't have enough " + WordUtils.capitalize(item.getCost().getKey().name().toLowerCase()) + "!")));
                        meta.lore(lore);

                        itemStack.setItemMeta(meta);

                        int itemPosition = lastItemPosition + (j == 7 || j == 14 ? 3 : 1);
                        inventory.setItem(itemPosition, itemStack);
                        lastItemPosition = itemPosition;
                    }
                }
            }
        } else {
            items.forEach(shopItem -> inventory.addItem(shopItem.getPreview()));
        }
    }

    @Override
    public final @NotNull Inventory getInventory() {
        return inventory;
    }
}
