package me.pycrs.bedwarsrecoded.inventory.menus.shop.shopItems;

import me.pycrs.bedwarsrecoded.inventory.menus.shop.dependency.BWCurrency;

public class ShopItemTier {
    private BWCurrency currency;
    private int price;
    private String description;

    public ShopItemTier(BWCurrency currency, int price, String description) {
        this.currency = currency;
        this.price = price;
        this.description = description;
    }
}
