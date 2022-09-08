package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.listeners.InventoryInteractListener;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static me.pycrs.bedwars.util.InventoryUtils.*;

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
    public void updateArmor(boolean replace) {
        if (armor == null) return;
        PlayerInventory inventory = bedwarsPlayer.getPlayer().getInventory();
        Color color = bedwarsPlayer.getTeam().getTeamColor().getColor();
        // Color all the armor pieces accordingly and equip the player, armor that can't be colored is skipped
        if (inventory.getHelmet() == null || replace)
            inventory.setHelmet(new ItemBuilder(armor.getHelmet())
                    .addRole(ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
                    .setArmorColor(color)
                    .build());
        if (inventory.getChestplate() == null || replace)
            inventory.setChestplate(new ItemBuilder(armor.getChestplate())
                    .addRole(ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
                    .setArmorColor(color)
                    .build());
        if (inventory.getLeggings() == null || replace)
            inventory.setLeggings(new ItemBuilder(armor.getLeggings())
                    .addRole(ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
                    .setArmorColor(color)
                    .build());
        if (inventory.getBoots() == null || replace)
            inventory.setBoots(new ItemBuilder(armor.getBoots())
                    .addRole(ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
                    .setArmorColor(color)
                    .build());
    }

    public void updateEquipment() {
        updateEquipment(false);
    }

    /**
     * Makes sure the player has all up-to-date tools
     **/
    public void updateEquipment(boolean checkSword) {
        Player player = bedwarsPlayer.getPlayer();
        PlayerInventory inventory = bedwarsPlayer.getPlayer().getInventory();

        // Smart sword
        if (!hasASword(player)) {
            InventoryClickEvent lastEvent = InventoryInteractListener.LAST_EVENT.get(player.getUniqueId());
            if (!checkSword || (lastEvent != null && lastEvent.getAction() != InventoryAction.PICKUP_ALL))
                inventory.addItem(Sword.getDefault().getItemStack());
        } else if (checkSword) {
            if (hasDefaultSword(player) && getAmountOfSwords(player) > 1)
                for (int i = 0; i < player.getInventory().getContents().length; i++) {
                    ItemStack currentItem = player.getInventory().getItem(i);
                    if (currentItem == null) continue;
                    if (Sword.getDefault().getItemStack().getType() == currentItem.getType())
                        player.getInventory().setItem(i, null);
                }
        }

        // A tool is given to a player only if they don't have any kind in their inventory, then depending on the circumstances, a tool is given accordingly
        if (!hasAPickaxe(player) && pickaxe != Pickaxe.NONE) inventory.addItem(pickaxe.getItemStack());

        if (!hasAnAxe(player) && axe != Axe.NONE) inventory.addItem(axe.getItemStack());

        if (shears && !InventoryUtils.hasAtLeastOne(player, Material.SHEARS)) inventory.addItem(SHEARS);

        if (!InventoryUtils.hasAtLeastOne(player, Material.COMPASS)) inventory.setItem(8, COMPASS);
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
