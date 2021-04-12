package me.pycrs.bedwarsrecoded.inventory.shops;

import org.bukkit.Material;

public class GenericShop extends Shop {
    public GenericShop() {
        super();
    }

    @Override
    public boolean categorical() {
        return true;
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void setCategories() {
        categories.put(new ShopCategory("Blocks", Material.TERRACOTTA,
                new ShopItem(Material.WHITE_WOOL, 16, BWCurrency.IRON, 4, "Great for bridging across islands. Turns into your team's color."),
                new ShopItem(Material.OAK_PLANKS, 16, BWCurrency.GOLD, 4, "Good block to defend your bed. Strong against pickaxes."),
                new ShopItem(Material.END_STONE, 12, BWCurrency.IRON, 24, "Solid block to defend your bed."),
                new ShopItem(Material.TERRACOTTA, 16, BWCurrency.IRON, 12, "Basic block to defend your bed."),
                new ShopItem(Material.GLASS, 4, BWCurrency.IRON, 12, "Immune to explosions"),
                new ShopItem(Material.LADDER, 16, BWCurrency.IRON, 4, "Useful to save cats stuck in trees.")), true);
        categories.put(new ShopCategory("Melee", Material.GOLDEN_SWORD,
                new ShopItem(Material.STONE_SWORD, 1, BWCurrency.IRON, 10, null)), false);
        categories.put(new ShopCategory("Armor", Material.CHAINMAIL_BOOTS,
                new ShopItem(Material.BARRIER, 1, BWCurrency.EMERALD, 64, null)), false);
        categories.put(new ShopCategory("Tools", Material.STONE_PICKAXE,
                new ShopItem(Material.BARRIER, 1, BWCurrency.EMERALD, 64, null)), false);
        categories.put(new ShopCategory("Ranged", Material.BOW,
                new ShopItem(Material.BARRIER, 1, BWCurrency.EMERALD, 64, null)), false);
        categories.put(new ShopCategory("Potions", Material.BREWING_STAND,
                new ShopItem(Material.BARRIER, 1, BWCurrency.EMERALD, 64, null)), false);
        categories.put(new ShopCategory("Utility", Material.TNT,
                new ShopItem(Material.BARRIER, 1, BWCurrency.EMERALD, 64, null)), false);
    }
}
