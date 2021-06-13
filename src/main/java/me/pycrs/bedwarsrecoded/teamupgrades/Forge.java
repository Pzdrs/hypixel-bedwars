package me.pycrs.bedwarsrecoded.teamupgrades;

public enum Forge {
    IRON_FORGE(1.5,"+50% Resources",false),
    GOLDEN_FORGE(2,"+100% Resources",false),
    EMERALD_FORGE(2,"Spawn emeralds",true),
    MOLTEN_FORGE(3,"+200% Resources",true);

    private double multiplier;
    private String description;
    private boolean canSpawnEmeralds;

    Forge(double multiplier, String description, boolean canSpawnEmeralds) {
        this.multiplier = multiplier;
        this.description = description;
        this.canSpawnEmeralds = canSpawnEmeralds;
    }
}
