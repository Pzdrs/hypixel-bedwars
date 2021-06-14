package me.pycrs.bedwars.teamupgrades;

public enum ReinforcedArmor {
    PROTECTION_1("Protection I", 1),
    PROTECTION_2("Protection II", 2),
    PROTECTION_3("Protection III", 3),
    PROTECTION_4("Protection IV", 4);

    private String description;
    private int tier;

    ReinforcedArmor(String description, int tier) {
        this.description = description;
        this.tier = tier;
    }

    public String getDescription() {
        return description;
    }

    public int getTier() {
        return tier;
    }
}
