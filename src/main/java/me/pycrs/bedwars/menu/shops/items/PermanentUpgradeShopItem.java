package me.pycrs.bedwars.menu.shops.items;

import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PermanentUpgradeShopItem extends ShopItem {
    public PermanentUpgradeShopItem(String id, Material material, int amount, BWCurrency currency, int price, String description) {
        super(id, material, amount, currency, price, description);
    }

    public PermanentUpgradeShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        super(id, itemStack, currency, price, description);
    }

    @Override
    protected ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData(ItemBuilder.ROLES_KEY, PersistentDataType.STRING, "shopItem")
                .setPersistentData(ShopItem.ITEM_ID_KEY, PersistentDataType.STRING, id)
                .setLore(Component.text("Cost: ", NamedTextColor.GRAY).append(getCost().getDisplay()), Component.empty())
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .build();
    }

    @Override
    public boolean purchase(Player player) {
        System.out.println("permanent diamond upgrade: " + id);
        return false;
    }
}
