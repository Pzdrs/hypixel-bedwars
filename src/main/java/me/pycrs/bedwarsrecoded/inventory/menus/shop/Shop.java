package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Utils;
import me.pycrs.bedwarsrecoded.inventory.menus.Menu;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopItem;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Shop extends Menu {
    protected LinkedList<ShopCategory> categories;
    protected ShopCategory selectedCategory;

    public Shop(Player player) {
        super(player);
        this.categories = new LinkedList<>();
    }

    @Override
    public final Component getName() {
        return Component.text(selectedCategory.getName());
    }

    protected abstract String getDefaultCategory();

    public abstract void setCategories();

    public void setSelectedCategory(String id) {
        this.selectedCategory = categories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
    }

    private void setActiveCategory() {
        setSelectedCategory(getDefaultCategory());
    }

    private void setupInventory() {
        this.inventory = Bukkit.createInventory(this, getSize(), Component.text(selectedCategory.getName()));
        setContent();
    }

    @Override
    public final void open() {
        categories.clear();
        setCategories();
        setActiveCategory();

        setupInventory();
        player.openInventory(inventory);
    }

    // FIXME: 4/13/2021 players swinging his hand when navigation to a different category
    private void navigate(String category) {
        setSelectedCategory(category);
        open();
    }

    @Override
    public final void setContent() {
        if (categories.size() > 1) MenuUtils.displayCategories(inventory, categories, selectedCategory);

        for (int i = 0; i < categories.size(); i++) {
            ShopCategory category = categories.get(i);
            boolean active = selectedCategory.getId().equals(category.getId());
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
    }
}
