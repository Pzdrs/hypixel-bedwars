package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.MenuButtonHandler;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems.GenericShopItem;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems.PermanentUpgrade;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TeamUpgradesShop extends Shop {
    public TeamUpgradesShop(Player player) {
        super(player);
    }

    @Override
    protected int getSize() {
        return 45;
    }

    @Override
    protected String getDefaultCategory() {
        return "default";
    }

    @Override
    protected void setCategories() {
        categories.add(new ShopCategory("default", "Upgrades & Traps", Material.SPONGE,
                new PermanentUpgrade("sharpness",
                        new ItemBuilder(Material.IRON_SWORD)
                                .setDisplayName("Sharpened Swords")
                                .build(), BWCurrency.DIAMOND, 4, "Your team permanently gains\nSharpness I on all swords and\naxes!", true),
                new PermanentUpgrade("heal_pool",
                        new ItemBuilder(Material.BEACON)
                                .setDisplayName("Heal Pool")
                                .build(), BWCurrency.DIAMOND, 1, "Creates a Regeneration field\naround your base!", true),
                new PermanentUpgrade("dragon_buff",
                        new ItemBuilder(Material.DRAGON_EGG)
                                .setDisplayName("Dragon Buff")
                                .build(), BWCurrency.DIAMOND, 2, "Your team will have 2 dragons\ninstead of1 during deathmatch!", true)));
        categories.add(new ShopCategory("traps", "Queue a trap", Material.TRIPWIRE_HOOK, new GenericShopItem("kokot", Material.TRIPWIRE_HOOK, 1, BWCurrency.EMERALD, 1, null)));
    }

    @Override
    public void setContent() {
        MenuUtils.fillRow(3, inventory, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName("&8\u2191 &7Purchasable")
                .setLore("&8\u2193 &7Traps Queue")
                .build());
        MenuUtils.addPurchasableItems(9, inventory, selectedCategory.getItems(), player);
        MenuUtils.createButton(new ItemBuilder(Material.LEATHER)
                .setDisplayName(Component.text("Buy a trap", NamedTextColor.YELLOW))
                .setItemDescription("Purchased traps will be\nqueued on the right.", ChatColor.GRAY)
                .build(), inventory, 16, () -> new TrapsShop(player).open());
    }

    @Override
    protected void handlePurchase(InventoryClickEvent event) {

    }
}
