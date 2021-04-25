package me.pycrs.bedwarsrecoded;

public enum Forge {
    IRON_FORGE(1.5, false),
    GOLDEN_FORGE(2, false),
    EMERALD_FORGE(2, true),
    MOLTEN_FORGE(3, true);

    private double multiplier;
    private boolean canSpawnEmeralds;

    Forge(double multiplier, boolean canSpawnEmeralds) {
        this.multiplier = multiplier;
        this.canSpawnEmeralds = canSpawnEmeralds;
    }
}
