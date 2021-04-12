package me.pycrs.bedwarsrecoded.inventory.shops;

import me.pycrs.bedwarsrecoded.BTeam;
import org.bukkit.Material;

public class GenericShop extends Shop {
    private BTeam team;

    public GenericShop(BTeam team) {
        super();
        this.team = team;
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
        categories.put(new ShopCategory("Quick Buy", Material.NETHER_STAR), false);
        categories.put(new ShopCategory("Blocks", Material.TERRACOTTA,
                new ShopItem(Material.WHITE_WOOL, 16, BWCurrency.IRON, 4, "Great for bridging across\nislands. Turns into your team's\ncolor."),
                new ShopItem(Material.OAK_PLANKS, 16, BWCurrency.GOLD, 4, "Good block to defend your bed.\nStrong against pickaxes."),
                new ShopItem(Material.END_STONE, 12, BWCurrency.IRON, 24, "Solid block to defend your bed."),
                new ShopItem(Material.TERRACOTTA, 16, BWCurrency.IRON, 12, "Basic block to defend your bed."),
                new ShopItem(Material.GLASS, 4, BWCurrency.IRON, 12, "Immune to explosions"),
                new ShopItem(Material.LADDER, 16, BWCurrency.IRON, 4, "Useful to save cats stuck in\ntrees.")), true);
        categories.put(new ShopCategory("Melee", Material.GOLDEN_SWORD), false);
        categories.put(new ShopCategory("Armor", Material.CHAINMAIL_BOOTS), false);
        categories.put(new ShopCategory("Tools", Material.STONE_PICKAXE), false);
        categories.put(new ShopCategory("Ranged", Material.BOW), false);
        categories.put(new ShopCategory("Potions", Material.BREWING_STAND), false);
        categories.put(new ShopCategory("Utility", Material.TNT), false);
    }
}
