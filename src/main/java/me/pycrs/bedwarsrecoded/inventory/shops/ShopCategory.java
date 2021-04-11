package me.pycrs.bedwarsrecoded.inventory.shops;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ShopCategory {
    QUICK_BUY("Quick Buy", Material.NETHER_STAR),
    BLOCKS("Blocks", Material.TERRACOTTA),
    WEAPONS("Weapons", Material.GOLDEN_SWORD),
    ARMOR("Armor", Material.CHAINMAIL_BOOTS),
    TOOLS("Tools", Material.STONE_PICKAXE),
    LONG_RANGE_WEAPONS("Bows and arrows", Material.BOW),
    POTIONS("Potions", Material.BREWING_STAND),
    SPECIALS("Specials", Material.TNT);

    private String title;
    private Material preview;
    private List<ShopItem> items;

    ShopCategory(String title, Material preview) {
        this.title = title;
        this.preview = preview;
        this.items = new ArrayList<>();
    }

    public void setItems(ShopItem... items) {
        this.items.addAll(Arrays.asList(items));
    }
}
