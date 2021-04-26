package me.pycrs.bedwarsrecoded;

public enum ReinforcedArmor {
    DEFAULT_PROTECTION(0, null),
    PROTECTION_1(2, "Protection I"),
    PROTECTION_2(4,"Protection II"),
    PROTECTION_3(8,"Protection III"),
    PROTECTION_4(16,"Protection IV");

    private String description;
    private int price;
    private int protection;

    ReinforcedArmor(int price, String description) {
        this.description = description;
        this.price = price;
    }
}
