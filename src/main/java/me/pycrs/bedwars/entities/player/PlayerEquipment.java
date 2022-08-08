package me.pycrs.bedwars.entities.player;

import org.bukkit.inventory.ItemStack;

public class PlayerEquipment {
    public static final ItemStack COMPASS = null;

    private Armor armor;
    private Pickaxe pickaxe;
    private Axe axe;
    private boolean shears;

    public PlayerEquipment(Armor armor, Pickaxe pickaxe, Axe axe, boolean shears) {
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
