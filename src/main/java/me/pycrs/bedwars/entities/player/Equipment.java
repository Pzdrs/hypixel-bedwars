package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.BedwarsItemBuilder;
import org.bukkit.inventory.ItemStack;

public interface Equipment {
    ItemStack getItemStack();

    default ItemStack applyEquipmentMeta(ItemStack itemStack) {
        return new BedwarsItemBuilder(itemStack)
                .addRole(BedwarsItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
                .setUnbreakable(true)
                .build();
    }
}
