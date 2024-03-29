package me.pycrs.bedwars.menu.shops;

import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.Menu;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.MenuUtils;
import me.pycrs.bedwars.menu.shops.dependency.ShopCategory;
import me.pycrs.bedwars.menu.shops.items.ShopItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.LinkedList;
import java.util.Optional;

public abstract class Shop extends Menu {
    public enum CategoryCycleDirection {
        LEFT, RIGHT
    }

    protected LinkedList<ShopCategory> categories;
    protected ShopCategory selectedCategory;
    protected BedwarsTeam team;

    public Shop(Player player) {
        super(player);
        this.categories = new LinkedList<>();
        this.team = BedwarsPlayer.toBedwarsPlayer(player).getTeam();

        setCategories();
        setupSelectedCategory();
    }

    @Override
    public final Component getTitle() {
        return Component.text(selectedCategory.getName());
    }

    @Override
    public final void open() {
        this.inventory = Bukkit.createInventory(this, getSize(), getTitle());
        render();
        player.openInventory(inventory);
    }

    @Override
    public void setContent() {
        MenuUtils.displayCategories(inventory, categories, selectedCategory);
        MenuUtils.addPurchasableItems(inventory, selectedCategory.getItems(), player);
    }

    @Override
    public final void handle(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        switch (MenuUtils.getItemRole(event.getCurrentItem())) {
            case "category" -> {
                InventoryUtils.getPersistentData(event.getCurrentItem(),
                        new NamespacedKey(Bedwars.getInstance(), "category"), PersistentDataType.STRING, this::setSelectedCategory);
                open();
            }
            case "shopItem" -> handlePurchase(event);
            default -> System.out.println("handle case default");
        }
    }

    protected abstract String getDefaultCategory();

    protected abstract void setCategories();

    private void handlePurchase(InventoryClickEvent event) {
        ShopItem item = MenuUtils.getItemById(selectedCategory, event.getCurrentItem().getItemMeta()
                .getPersistentDataContainer().get(new NamespacedKey(Bedwars.getInstance(), "itemId"), PersistentDataType.STRING));
        if (item != null)
            if (item.purchase(player)) render();
    }

    public final void cycleCategory(CategoryCycleDirection direction) {
        int currentIndex = MenuUtils.getCategoryIndex(categories, selectedCategory);
        switch (direction) {
            case LEFT:
                setSelectedCategory(--currentIndex);
                break;
            case RIGHT:
                setSelectedCategory(++currentIndex);
                break;
        }
    }

    public void setSelectedCategory(String id) {
        if (selectedCategory != null && selectedCategory.getId().equals(id)) return;
        this.selectedCategory = categories.stream().filter(category -> category.getId().equals(id)).findFirst().orElse(null);
    }

    public void setSelectedCategory(int index) {
        if (index < 0 || index > categories.size() - 1) return;
        this.selectedCategory = categories.get(index);
        open();
    }

    private void setupSelectedCategory() {
        if (selectedCategory == null) setSelectedCategory(getDefaultCategory());
    }

    protected final void render() {
        inventory.clear();
        setContent();
    }
}
