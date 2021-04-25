package me.pycrs.bedwarsrecoded.inventory.menus.shop;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopCategory;
import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.ShopItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
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

    // FIXME: 4/13/2021 Different prices per mode
    @Override
    protected void setCategories() {
        categories.add(new ShopCategory("quick_buy", "Quick Buy", Material.NETHER_STAR));
        categories.add(new ShopCategory("blocks", "Blocks", Material.TERRACOTTA,
                new ShopItem("wool",
                        new ItemBuilder(Material.WHITE_WOOL, 16)
                                .setDisplayName("Wool")
                                .build(), BWCurrency.IRON, 4, "Great for bridging across\nislands. Turns into your team's\ncolor."),
                new ShopItem("clay", Material.TERRACOTTA, 16, BWCurrency.IRON, 12, "Basic block to defend your bed."),
                new ShopItem("blast-proof_glass",
                        new ItemBuilder(Material.GLASS, 4)
                                .setDisplayName("Blast-proof Glass")
                                .build(), BWCurrency.IRON, 12, "Immune to explosions"),
                new ShopItem("end_stone", Material.END_STONE, 12, BWCurrency.IRON, 24, "Solid block to defend your bed."),
                new ShopItem("ladder", Material.LADDER, 16, BWCurrency.IRON, 4, "Useful to save cats stuck in\ntrees."),
                new ShopItem("wood", Material.OAK_PLANKS, 16, BWCurrency.GOLD, 4, "Good block to defend your bed.\nStrong against pickaxes.")));
        categories.add(new ShopCategory("melee", "Melee", Material.GOLDEN_SWORD,
                new ShopItem("stone_sword",
                        new ItemBuilder(Material.STONE_SWORD)
                                .setFlags(ItemFlag.HIDE_ATTRIBUTES)
                                .build(), BWCurrency.IRON, 10, null),
                new ShopItem("iron_sword",
                        new ItemBuilder(Material.IRON_SWORD)
                                .setFlags(ItemFlag.HIDE_ATTRIBUTES)
                                .build(), BWCurrency.GOLD, 7, null),
                new ShopItem("diamond_sword",
                        new ItemBuilder(Material.DIAMOND_SWORD)
                                .setFlags(ItemFlag.HIDE_ATTRIBUTES)
                                .build(), BWCurrency.EMERALD, 4, null),
                new ShopItem("knockback_stick",
                        new ItemBuilder(Material.STICK)
                                .setDisplayName("Stick")
                                .addEnchantment(Enchantment.KNOCKBACK, 1)
                                .setFlags(ItemFlag.HIDE_ENCHANTS)
                                .build(), BWCurrency.GOLD, 6, null)));
        categories.add(new ShopCategory("armor", "Armor", Material.CHAINMAIL_BOOTS));
        categories.add(new ShopCategory("tools", "Tools", Material.STONE_PICKAXE));
        categories.add(new ShopCategory("ranged", "Ranged", Material.BOW,
                new ShopItem("arrow", Material.ARROW, 4, BWCurrency.GOLD, 4, null),
                new ShopItem("bow", Material.BOW, 1, BWCurrency.GOLD, 12, null),
                new ShopItem("power_bow",
                        new ItemBuilder(Material.BOW)
                                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                                .build(), BWCurrency.GOLD, 24, null),
                new ShopItem("punch_bow",
                        new ItemBuilder(Material.BOW)
                                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                                .addEnchantment(Enchantment.ARROW_KNOCKBACK, 1)
                                .build(), BWCurrency.EMERALD, 6, null)));
        // TODO: 4/13/2021 create potion builder
        categories.add(new ShopCategory("potions", "Potions", Material.BREWING_STAND));
        categories.add(new ShopCategory("utility", "Utility", Material.TNT,
                new ShopItem("golden_apple", Material.GOLDEN_APPLE, 1, BWCurrency.GOLD, 3, "Well-rounded healing."),
                new ShopItem("silverfish",
                        new ItemBuilder(Material.SNOWBALL)
                                .setDisplayName("Bedbug")
                                .build(), BWCurrency.IRON, 40, "Spawns silverfish where the\nsnowball lands to distract your\nenemies."),
                new ShopItem("iron_golem",
                        new ItemBuilder(Material.POLAR_BEAR_SPAWN_EGG)
                                .setDisplayName("Dream Defender")
                                .build(), BWCurrency.IRON, 120, "Iron Golem to help defend your\nbase."),
                new ShopItem("fireball",
                        new ItemBuilder(Material.FIRE_CHARGE)
                                .setDisplayName("Fireball")
                                .build(), BWCurrency.IRON, 40, "Right-click to launch! Great to\nknock back enemies walking on\nthin bridges.."),
                new ShopItem("tnt",
                        new ItemBuilder(Material.TNT)
                                .setDisplayName("TNT")
                                .build(), BWCurrency.GOLD, 4, "Instantly ignites, appropriate\nto explode things!"),
                new ShopItem("ender_pearl", Material.ENDER_PEARL, 1, BWCurrency.EMERALD, 4, "The quickest way to invade enemy\nbases."),
                new ShopItem("water_bucket", Material.WATER_BUCKET, 1, BWCurrency.GOLD, 6, "Great to slow down approaching\nenemies. Can also protect\nagainst TNT."),
                new ShopItem("bridge_egg",
                        new ItemBuilder(Material.EGG)
                                .setDisplayName("Bridge Egg")
                                .build(), BWCurrency.EMERALD, 2, "This egg creates a abridge in its\ntrail after being thrown."),
                new ShopItem("magic_milk",
                        new ItemBuilder(Material.MILK_BUCKET)
                                .setDisplayName("Magic Milk")
                                .build(), BWCurrency.GOLD, 4, "Avoid triggering traps for 30\nseconds after consuming."),
                new ShopItem("sponge", Material.SPONGE, 4, BWCurrency.GOLD, 6, "Great for soaking up water."),
                new ShopItem("popup_tower",
                        new ItemBuilder(Material.CHEST)
                                .setDisplayName("Compact Pop-up Tower")
                                .build(), BWCurrency.IRON, 24, "Place a pop-up defence!")));
    }
}
