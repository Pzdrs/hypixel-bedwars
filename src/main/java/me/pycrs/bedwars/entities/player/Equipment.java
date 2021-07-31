package me.pycrs.bedwars.entities.player;

public class Equipment {
    enum Armor {
        DEFAULT, CHAIN_ARMOR, IRON_ARMOR, DIAMOND_ARMOR
    }

    private Armor armor;
    private Pickaxe pickaxe;
    private Axe axe;
    private boolean shears;

    public Equipment(Armor armor, Pickaxe pickaxe, Axe axe, boolean shears) {
        this.armor = armor;
        this.pickaxe = pickaxe;
        this.axe = axe;
        this.shears = shears;
    }

    public Armor getArmor() {
        return armor;
    }

    public Pickaxe getPickaxe() {
        return pickaxe;
    }

    public Axe getAxe() {
        return axe;
    }

    public boolean hasShears() {
        return shears;
    }
}
