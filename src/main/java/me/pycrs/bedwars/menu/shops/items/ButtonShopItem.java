package me.pycrs.bedwars.menu.shops.items;

import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.menu.shops.items.dependency.ShopButtonClickHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ButtonShopItem extends ShopItem {
    private ShopButtonClickHandler handler;

    public ButtonShopItem(String id, Material material, int amount, BWCurrency currency, int price, ShopButtonClickHandler handler, String description) {
        super(id, material, amount, currency, price, description);
        this.handler = handler;
    }

    public ButtonShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, ShopButtonClickHandler handler, String description) {
        super(id, itemStack, currency, price, description);
        this.handler = handler;
    }

    @Override
    protected ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData(ItemBuilder.ROLES_KEY, PersistentDataType.STRING, "shopItem")
                .setPersistentData(ShopItem.ITEM_ID_KEY, PersistentDataType.STRING, id)
                .setLore(Component.text("Cost: ", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                        .append(getCost().getDisplay()), Component.empty())
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .build();
    }

    @Override
    public boolean purchase(Player player) {
        return handler.handle();
    }
}
