package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerEquipment {
    public static final ItemStack COMPASS = new ItemBuilder(Material.COMPASS)
            .addRole(ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
            .build();
    private static final ItemStack SHEARS = new ItemBuilder(Material.SHEARS)
            .addRole(ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
            .build();

    private final BedwarsPlayer bedwarsPlayer;
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

    /**
     * Gives the player all the necessary tools
     **/
    public void equip() {
        PlayerInventory inventory = bedwarsPlayer.getPlayer().getInventory();

        if (!hasASword())
            inventory.addItem(Sword.getDefault().getItemStack());

        // A tool is given to a player only if they don't have any kind in their inventory, then depending on the circumstances, a tool is given accordingly
        if (!hasAPickaxe() && pickaxe != Pickaxe.NONE)
            inventory.addItem(pickaxe.getItemStack());

        if (!hasAnAxe() && axe != Axe.NONE)
            inventory.addItem(axe.getItemStack());

        if (shears && !hasShears()) {
            inventory.addItem(SHEARS);
        }

        if (!hasACompass()) inventory.setItem(8, COMPASS);
    }

    private boolean hasAPickaxe() {
        return InventoryUtils.hasAtLeastOne(bedwarsPlayer.getPlayer(),
                Material.WOODEN_PICKAXE,
                Material.STONE_PICKAXE,
                Material.GOLDEN_PICKAXE,
                Material.IRON_PICKAXE,
                Material.DIAMOND_PICKAXE,
                Material.NETHERITE_PICKAXE
        );
    }

    private boolean hasAnAxe() {
        return InventoryUtils.hasAtLeastOne(bedwarsPlayer.getPlayer(),
                Material.WOODEN_AXE,
                Material.STONE_AXE,
                Material.GOLDEN_AXE,
                Material.IRON_AXE,
                Material.DIAMOND_AXE,
                Material.NETHERITE_AXE
        );
    }

    private boolean hasACompass() {
        return InventoryUtils.hasAtLeastOne(bedwarsPlayer.getPlayer(), Material.COMPASS);
    }

    public boolean hasASword() {
        return InventoryUtils.hasAtLeastOne(bedwarsPlayer.getPlayer(),
                Material.WOODEN_SWORD,
                Material.STONE_SWORD,
                Material.GOLDEN_SWORD,
                Material.IRON_SWORD,
                Material.DIAMOND_SWORD,
                Material.NETHERITE_SWORD
        );
    }

    private boolean hasShears() {
        return InventoryUtils.hasAtLeastOne(bedwarsPlayer.getPlayer(), Material.SHEARS);
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

    public boolean hasShearsUnlocked() {
        return shears;
    }
}
