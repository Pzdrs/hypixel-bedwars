package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menus.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems.GenericShopItem;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems.ShopItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

public class GenericShop extends Shop {
    public GenericShop(Player player) {
        super(player);
    }

    @Override
    protected int getSize() {
        return 54;
    }

    @Override
    protected String getDefaultCategory() {
        return "blocks";
    }

    @Override
    protected void handlePurchase(InventoryClickEvent event) {
        ShopItem item = MenuUtils.getItemById(selectedCategory, event.getCurrentItem().getItemMeta()
                .getPersistentDataContainer().get(new NamespacedKey(BedWars.getInstance(), "itemId"), PersistentDataType.STRING));
        if (item != null)
            if (item.purchase(player)) render();
    }

    // TODO: 4/13/2021 Different prices per mode
    // TODO: 4/25/2021 per player quick buy menu
    @Override
    protected void setCategories() {
        categories.add(new ShopCategory("quick_buy", "Quick Buy", Material.NETHER_STAR));
        categories.add(new ShopCategory("blocks", "Blocks", Material.TERRACOTTA,
                new GenericShopItem("wool",
                        new ItemBuilder(Material.WHITE_WOOL, 16)
                                .setDisplayName("Wool")
                                .build(), BWCurrency.IRON, 4, "Great for bridging across\nislands. Turns into your team's\ncolor."),
                new GenericShopItem("clay",
                        new ItemBuilder(Material.TERRACOTTA, 16)
                                .setDisplayName("Hardened Clay")
                                .build(), BWCurrency.IRON, 12, "Basic block to defend your bed."),
                new GenericShopItem("blast-proof_glass",
                        new ItemBuilder(Material.GLASS, 4)
                                .setDisplayName("Blast-proof Glass")
                                .build(), BWCurrency.IRON, 12, "Immune to explosions"),
                new GenericShopItem("end_stone", Material.END_STONE, 12, BWCurrency.IRON, 24, "Solid block to defend your bed."),
                new GenericShopItem("ladder", Material.LADDER, 16, BWCurrency.IRON, 4, "Useful to save cats stuck in\ntrees."),
                new GenericShopItem("wood", Material.OAK_PLANKS, 16, BWCurrency.GOLD, 4, "Good block to defend your bed.\nStrong against pickaxes.")));
        categories.add(new ShopCategory("melee", "Melee", Material.GOLDEN_SWORD,
                new GenericShopItem("stone_sword", Material.STONE_SWORD, 1, BWCurrency.IRON, 10, null),
                new GenericShopItem("iron_sword", Material.IRON_SWORD, 1, BWCurrency.GOLD, 7, null),
                new GenericShopItem("diamond_sword", Material.DIAMOND_SWORD, 1, BWCurrency.EMERALD, 4, null),
                new GenericShopItem("knockback_stick",
                        new ItemBuilder(Material.STICK)
                                .setDisplayName("Stick")
                                .addEnchantment(Enchantment.KNOCKBACK, 1)
                                .build(), BWCurrency.GOLD, 6, null)));
        categories.add(new ShopCategory("armor", "Armor", Material.CHAINMAIL_BOOTS));
        categories.add(new ShopCategory("tools", "Tools", Material.STONE_PICKAXE));
        categories.add(new ShopCategory("ranged", "Ranged", Material.BOW,
                new GenericShopItem("arrow", Material.ARROW, 4, BWCurrency.GOLD, 4, null),
                new GenericShopItem("bow", Material.BOW, 1, BWCurrency.GOLD, 12, null),
                new GenericShopItem("power_bow",
                        new ItemBuilder(Material.BOW)
                                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                                .build(), BWCurrency.GOLD, 24, null),
                new GenericShopItem("punch_bow",
                        new ItemBuilder(Material.BOW)
                                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                                .addEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
                                .build(), BWCurrency.EMERALD, 6, null)));
        // TODO: 4/13/2021 create potion builder
        categories.add(new ShopCategory("potions", "Potions", Material.BREWING_STAND));
        categories.add(new ShopCategory("utility", "Utility", Material.TNT,
                new GenericShopItem("golden_apple", Material.GOLDEN_APPLE, 1, BWCurrency.GOLD, 3, "Well-rounded healing."),
                new GenericShopItem("silverfish",
                        new ItemBuilder(Material.SNOWBALL)
                                .setDisplayName("Bedbug")
                                .build(), BWCurrency.IRON, 40, "Spawns silverfish where the\nsnowball lands to distract your\nenemies."),
                new GenericShopItem("iron_golem",
                        new ItemBuilder(Material.POLAR_BEAR_SPAWN_EGG)
                                .setDisplayName("Dream Defender")
                                .build(), BWCurrency.IRON, 120, "Iron Golem to help defend your\nbase."),
                new GenericShopItem("fireball",
                        new ItemBuilder(Material.FIRE_CHARGE)
                                .setDisplayName("Fireball")
                                .build(), BWCurrency.IRON, 40, "Right-click to launch! Great to\nknock back enemies walking on\nthin bridges.."),
                new GenericShopItem("tnt",
                        new ItemBuilder(Material.TNT)
                                .setDisplayName("TNT")
                                .build(), BWCurrency.GOLD, 4, "Instantly ignites, appropriate\nto explode things!"),
                new GenericShopItem("ender_pearl", Material.ENDER_PEARL, 1, BWCurrency.EMERALD, 4, "The quickest way to invade enemy\nbases."),
                new GenericShopItem("water_bucket", Material.WATER_BUCKET, 1, BWCurrency.GOLD, 6, "Great to slow down approaching\nenemies. Can also protect\nagainst TNT."),
                new GenericShopItem("bridge_egg",
                        new ItemBuilder(Material.EGG)
                                .setDisplayName("Bridge Egg")
                                .build(), BWCurrency.EMERALD, 2, "This egg creates a abridge in its\ntrail after being thrown."),
                new GenericShopItem("magic_milk",
                        new ItemBuilder(Material.MILK_BUCKET)
                                .setDisplayName("Magic Milk")
                                .build(), BWCurrency.GOLD, 4, "Avoid triggering traps for 30\nseconds after consuming."),
                new GenericShopItem("sponge", Material.SPONGE, 4, BWCurrency.GOLD, 6, "Great for soaking up water."),
                new GenericShopItem("popup_tower",
                        new ItemBuilder(Material.CHEST)
                                .setDisplayName("Compact Pop-up Tower")
                                .build(), BWCurrency.IRON, 24, "Place a pop-up defence!")));
    }
}
