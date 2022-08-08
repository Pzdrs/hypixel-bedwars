package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.menu.shops.items.ShopItem;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

public class PlayerEquipment {
    public static final ItemStack COMPASS = null;

    private BedwarsPlayer bedwarsPlayer;
    private Armor armor;
    private Pickaxe pickaxe;
    private Axe axe;
    private boolean shears;

    public PlayerEquipment(BedwarsPlayer bedwarsPlayer, Armor armor, Pickaxe pickaxe, Axe axe, boolean shears) {
        this.bedwarsPlayer = bedwarsPlayer;
        this.armor = armor;
        this.pickaxe = pickaxe;
        this.axe = axe;
        this.shears = shears;
    }

    // TODO: 8/8/2022 team upgrades, i.e. protection
    public void updateArmor() {
        if (armor == null) return;
        PlayerInventory inventory = bedwarsPlayer.getPlayer().getInventory();
        Color color = bedwarsPlayer.getTeam().getTeamColor().getColor();
        // Color all the armor pieces accordingly and equip the player, armor that can't be colored is skipped
        inventory.setArmorContents(new ItemStack[]{
                new ItemBuilder(armor.getBoots())
                        .setArmorColor(color)
                        .build(),
                new ItemBuilder(armor.getLeggings())
                        .setArmorColor(color)
                        .build(),
                new ItemBuilder(armor.getChestplate())
                        .setArmorColor(color)
                        .build(),
                new ItemBuilder(armor.getHelmet())
                        .setArmorColor(color)
                        .build()
        });
    }

    public void equip() {
        PlayerInventory inventory = bedwarsPlayer.getPlayer().getInventory();
        inventory.addItem(new ItemBuilder(Material.WOODEN_SWORD)
                .setPersistentData(ShopItem.ROLE_KEY, PersistentDataType.STRING, ShopItem.Role.PERSISTENT_EQUIPMENT.key())
                .build());

        inventory.addItem(pickaxe.getItemStack());
        inventory.addItem(axe.getItemStack());

        if (shears) {
            inventory.addItem(new ItemBuilder(Material.SHEARS)
                    .setPersistentData(ShopItem.ROLE_KEY, PersistentDataType.STRING, ShopItem.Role.PERSISTENT_EQUIPMENT.key())
                    .build());
        }
    }

    public void updateEquipment() {

    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void setPickaxe(Pickaxe pickaxe) {
        this.pickaxe = pickaxe;
    }

    public void setAxe(Axe axe) {
        this.axe = axe;
    }

    public void setShears(boolean shears) {
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
