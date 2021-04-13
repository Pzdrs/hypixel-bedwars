package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.inventory.menus.Menu;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.LinkedList;

public abstract class Shop extends Menu {
    public enum CategoryCycleDirection {
        LEFT, RIGHT
    }

    protected LinkedList<ShopCategory> categories;
    private ShopCategory selectedCategory;

    public Shop(Player player) {
        super(player);
        this.categories = new LinkedList<>();
    }

    @Override
    public final Component getTitle() {
        return Component.text(selectedCategory.getName());
    }

    @Override
    public final void open() {
        System.out.println("open");
        render();
        player.openInventory(inventory);
    }

    @Override
    public final void setContent() {
        System.out.println("set content");
        if (categories.size() > 1) MenuUtils.displayCategories(inventory, categories, selectedCategory);
        MenuUtils.addPurchasableItems(inventory, selectedCategory.getItems(), player);
    }

    @Override
    public final void handle(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        switch (MenuUtils.getItemRole(event.getCurrentItem())) {
            case "category":
                // TODO: 4/13/2021 change category
                System.out.println(MenuUtils.getPDCValue(event.getCurrentItem(), "category"));
                break;
            case "shopItem":
                handlePurchase(event);
                break;
        }
    }

    protected abstract String getDefaultCategory();

    protected abstract void setCategories();

    protected abstract void handlePurchase(InventoryClickEvent event);

    public final void cycleCategory(CategoryCycleDirection direction) {
        switch (direction) {
            case LEFT:
                System.out.println("cycle left");
                break;
            case RIGHT:
                System.out.println("cycle right");
                break;
        }
    }

    public void setSelectedCategory(String id) {
        this.selectedCategory = categories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
    }

    private void setupSelectedCategory() {
        if (selectedCategory == null) setSelectedCategory(getDefaultCategory());
    }

    private void render() {
        categories.clear();
        setCategories();
        setupSelectedCategory();
        this.inventory = Bukkit.createInventory(this, getSize(), getTitle());
        setContent();
    }
}
