package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Trap;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menus.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems.PermanentUpgrade;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

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
    }

    @Override
    public void setContent() {
        MenuUtils.fillRow(3, inventory, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setDisplayName("&8\u2191 &7Purchasable")
                .setLore("&8\u2193 &7Traps Queue")
                .build());
        MenuUtils.addPurchasableItems(9, inventory, selectedCategory.getItems(), player);
        MenuUtils.createButton(new ItemBuilder(Material.LEATHER)
                .setDisplayName(Component.text("Buy a trap", NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false))
                .setItemDescription("Purchased traps will be\nqueued on the right.", ChatColor.GRAY)
                .addLoreLine("&eClick to browse!")
                .build(), this, 16, () -> new TrapsShop(player).open());
        // TODO: 4/26/2021 display traps queue, i really dont know how to do that rn
    }

    @Override
    protected void handlePurchase(InventoryClickEvent event) {

    }
}
