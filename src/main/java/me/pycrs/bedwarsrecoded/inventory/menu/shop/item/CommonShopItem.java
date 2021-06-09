package me.pycrs.bedwarsrecoded.inventory.menu.shop.item;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.ItemBuilder;
import me.pycrs.bedwarsrecoded.Utils;
import me.pycrs.bedwarsrecoded.inventory.menu.MenuUtils;
import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.BWCurrency;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CommonShopItem extends ShopItem {
    private ItemStack product;

    public CommonShopItem(String id, Material material, int amount, BWCurrency currency, int price, String description) {
        super(id, material, amount, currency, price, description);
        this.product = formatProductItem(material, amount);
    }

    public CommonShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        super(id, itemStack, currency, price, description);
        this.product = formatProductItem(itemStack);
    }

    @Override
    protected ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setPlugin(BedWars.getInstance())
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData("role", PersistentDataType.STRING, "shopItem")
                .setPersistentData("itemId", PersistentDataType.STRING, id)
                .setLore(Component.text("Cost: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false).append(getCost().getDisplay()), Component.empty())
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .build();
    }

    @Override
    public boolean purchase(Player player) {
        // TODO: 4/17/2021 add sounds
        if (player.getInventory().firstEmpty() == -1) {
            // Inventory full
            player.sendMessage(Component.text("Purchase Failed! Your inventory is full!", NamedTextColor.RED));
            return false;
        }
        int amount = MenuUtils.canAffordAmount(cost.getCurrency(), cost.getPrice(), player);
        if (amount != 0) {
            // Cannot afford the item
            player.sendMessage(Component.text("You don't have enough " + getCost().getCurrency().capitalize() + "! Need " + amount + " more!", NamedTextColor.RED));
            return false;
        }
        player.sendMessage(Component.text("You purchased ", NamedTextColor.GREEN)
                .append(getItemName(preview).color(NamedTextColor.GOLD)));
        player.getInventory().removeItem(new ItemStack(cost.getCurrency().getType(), cost.getPrice()));
        player.getInventory().addItem(product);
        return true;
    }

    private Component getItemName(ItemStack preview) {
        if (preview.hasItemMeta() && preview.getItemMeta().displayName() != null)
            return preview.getItemMeta().displayName();
        return Component.text(Utils.materialToFriendlyName(preview.getType()));
    }

    private ItemStack formatProductItem(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    private ItemStack formatProductItem(ItemStack itemStack) {
        return formatProductItem(itemStack.getType(), itemStack.getAmount());
    }
}
