package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems.GenericShopItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TrapsShop extends Shop {
    public TrapsShop(Player player) {
        super(player);
    }

    @Override
    protected int getSize() {
        return 27;
    }

    @Override
    protected String getDefaultCategory() {
        return "default";
    }

    @Override
    protected void setCategories() {
        categories.add(new ShopCategory("default", "Queue a trap", Material.SPONGE));
    }

    @Override
    public void setContent() {
        MenuUtils.addPurchasableItems(9, inventory, selectedCategory.getItems(), player);
        MenuUtils.createButton(new ItemBuilder(Material.ARROW)
                .setDisplayName(Component.text("Go Back", NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false))
                .addLoreLine("To Upgrades & Traps")
                .build(), this, 22, () -> new TeamUpgradesShop(player).open());
    }

    @Override
    protected void handlePurchase(InventoryClickEvent event) {
        System.out.println("trap purchased");
    }
}
