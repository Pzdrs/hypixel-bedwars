package me.pycrs.bedwarsrecoded.inventory.menu.shop;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Mode;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menu.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.item.PermanentUpgradeShopItem;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.item.dependency.ShopItemTier;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.item.TieredShopItem;
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
                new PermanentUpgradeShopItem("sharpness",
                        new ItemBuilder(Material.IRON_SWORD)
                                .setDisplayName("Sharpened Swords")
                                .build(), BWCurrency.DIAMOND, isSoloOrDoubles() ? 4 : 8,
                        "Your team permanently gains\nSharpness I on all swords and\naxes!"),
                new TieredShopItem("reinforced_armor", new ItemBuilder(Material.IRON_CHESTPLATE)
                        .setDisplayName("Reinforced Armor {tier}")
                        .build(), "Your team permanently gains\nProtection on all armor pieces!", 0,
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 2 : 5, "Protection I"),
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 4 : 10, "Protection II"),
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 8 : 20, "Protection III"),
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 16 : 30, "Protection IV")),
                new TieredShopItem("maniac_miner", new ItemBuilder(Material.GOLDEN_PICKAXE)
                        .setDisplayName("Maniac Miner {tier}")
                        .build(), "All players on your team\npermanently gain Haste.", 0,
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 2 : 4, "Haste I"),
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 4 : 6, "Haste II")),
                new TieredShopItem("forge", new ItemBuilder(Material.FURNACE)
                        .setDisplayName("{type} Forge")
                        .build(), "Upgrade resources spawning on\nyour island.", 0,
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 2 : 4, "+50% Resources"),
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 4 : 8, "+100% Resources"),
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 6 : 12, "Spawn emeralds"),
                        new ShopItemTier(BWCurrency.DIAMOND, isSoloOrDoubles() ? 8 : 16, "+200% Resources")),
                new PermanentUpgradeShopItem("heal_pool",
                        new ItemBuilder(Material.BEACON)
                                .setDisplayName("Heal Pool")
                                .build(), BWCurrency.DIAMOND, isSoloOrDoubles() ? 1 : 3,
                        "Creates a Regeneration field\naround your base!"),
                new PermanentUpgradeShopItem("dragon_buff",
                        new ItemBuilder(Material.DRAGON_EGG)
                                .setDisplayName("Dragon Buff")
                                .build(), BWCurrency.DIAMOND, 5,
                        "Your team will have 2 dragons\ninstead of 1 during deathmatch!")));
    }

    private boolean isSoloOrDoubles() {
        return BedWars.getMode().equals(Mode.SOLO) || BedWars.getMode().equals(Mode.DOUBLES);
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
}
