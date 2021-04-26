package me.pycrs.bedwarsrecoded.teamUpgrades;

public enum ManiacMiner {
    DEFAULT_HASTE(0, null),
    HASTE_1(2, "Haste I"),
    HASTE_2(4, "Haste II");

    private int price;
    private String description;

    ManiacMiner(int price, String description) {
        this.price = price;
        this.description = description;
    }
}
