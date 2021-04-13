package me.pycrs.bedwarsrecoded.inventory.shops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GenericShop extends Shop {
    public GenericShop(Player player) {
        super(player);
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
        categories.add(new ShopCategory("quick_buy", "Quick Buy", Material.NETHER_STAR));
        categories.add(new ShopCategory("blocks", "Blocks", Material.TERRACOTTA,
                new ShopItem(Material.WHITE_WOOL, 16, BWCurrency.IRON, 4, "Great for bridging across\nislands. Turns into your team's\ncolor."),
                new ShopItem(Material.OAK_PLANKS, 16, BWCurrency.GOLD, 4, "Good block to defend your bed.\nStrong against pickaxes."),
                new ShopItem(Material.END_STONE, 12, BWCurrency.IRON, 24, "Solid block to defend your bed."),
                new ShopItem(Material.TERRACOTTA, 16, BWCurrency.IRON, 12, "Basic block to defend your bed."),
                new ShopItem(Material.GLASS, 4, BWCurrency.IRON, 12, "Immune to explosions"),
                new ShopItem(Material.LADDER, 16, BWCurrency.IRON, 4, "Useful to save cats stuck in\ntrees.")));
        categories.add(new ShopCategory("melee", "Melee", Material.GOLDEN_SWORD));
        categories.add(new ShopCategory("armor", "Armor", Material.CHAINMAIL_BOOTS));
        categories.add(new ShopCategory("tools", "Tools", Material.STONE_PICKAXE));
        categories.add(new ShopCategory("ranged", "Ranged", Material.BOW));
        categories.add(new ShopCategory("potions", "Potions", Material.BREWING_STAND));
        categories.add(new ShopCategory("utility", "Utility", Material.TNT));

        // Default category
        if (activeCategory == null)
            setActiveCategory("blocks");
    }

    @Override
    public void handlePurchase(InventoryClickEvent event) {
        System.out.println(event.getCurrentItem().getType());
    }
}
