package me.pycrs.bedwarsrecoded.inventory.menu.shop.item.dependency;

import me.pycrs.bedwarsrecoded.inventory.menu.shop.dependency.BWCurrency;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

/**
 * Wrapper for the cost of an item
 */
public class Cost {
    private BWCurrency currency;
    private int price;

    public Cost(BWCurrency currency, int price) {
        this.currency = currency;
        this.price = price;
    }

    public BWCurrency getCurrency() {
        return currency;
    }

    public int getPrice() {
        return price;
    }

    /**
     * Puts the currency and the price together and spits out a formatted component
     *
     * @return Formatted price to display in the shop GUI
     */
    public Component getDisplay() {
        if (price < 1) {
            return Component.text("Free", NamedTextColor.GREEN);
        } else if (price > 1)
            return Component.text(price + " ", currency.getColor())
                    .append(Component.text(currency.capitalize() + (currency.isPlural() ? "s" : "")));
        return Component.text(price + " ", currency.getColor())
                .append(Component.text(currency.capitalize()));
    }
}
