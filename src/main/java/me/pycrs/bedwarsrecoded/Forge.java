package me.pycrs.bedwarsrecoded;

public enum Forge {
    DEFAULT_FORGE(1,0,null, false),
    IRON_FORGE(1.5, 4,"+50% Resources",false),
    GOLDEN_FORGE(2, 8,"+100% Resources",false),
    EMERALD_FORGE(2, 12,"Spawn emeralds",true),
    MOLTEN_FORGE(3, 16,"+200% Resources",true);

    private double multiplier;
    private int price;
    private String description;
    private boolean canSpawnEmeralds;

    Forge(double multiplier, int price, String description, boolean canSpawnEmeralds) {
        this.multiplier = multiplier;
        this.price = price;
        this.description = description;
        this.canSpawnEmeralds = canSpawnEmeralds;
    }
}
