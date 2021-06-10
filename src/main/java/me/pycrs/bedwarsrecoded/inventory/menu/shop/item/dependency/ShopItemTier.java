package me.pycrs.bedwarsrecoded.inventory.menu.shop.item.dependency;

public class ShopItemTier {
    private BWCurrency currency;
    private int price;
    private String description;

    public ShopItemTier(BWCurrency currency, int price, String description) {
        this.currency = currency;
        this.price = price;
        this.description = description;
    }

    public BWCurrency getCurrency() {
        return currency;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
