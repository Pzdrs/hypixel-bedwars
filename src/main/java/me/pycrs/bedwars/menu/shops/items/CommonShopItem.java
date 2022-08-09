package me.pycrs.bedwars.menu.shops.items;

import me.pycrs.bedwars.util.BedwarsItemBuilder;
import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.util.Utils;
import me.pycrs.bedwars.util.MenuUtils;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
        this.product = new ItemStack(material, amount);
    }

    public CommonShopItem(String id, ItemStack itemStack, BWCurrency currency, int price, String description) {
        super(id, itemStack.clone(), currency, price, description);
        // FIXME: 6/9/2021 items with custom names are italic when bought
        this.product = itemStack;
    }

    @Override
    protected ItemStack formatPreviewItem(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .setPersistentData(BedwarsItemBuilder.ROLES_KEY, PersistentDataType.STRING, Role.DEFAULT.key())
                .setPersistentData(ShopItem.ITEM_ID_KEY, PersistentDataType.STRING, id)
                .setLore(Component.text("Cost: ", NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false).append(getCost().getDisplay()), Component.empty())
                .setItemDescription(description == null ? null : description, ChatColor.GRAY)
                .previewEnchantments()
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
}
