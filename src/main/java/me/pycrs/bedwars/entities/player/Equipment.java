package me.pycrs.bedwars.entities.player;

import me.pycrs.bedwars.util.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public interface Equipment {
    ItemStack getItemStack();

    default ItemStack applyEquipmentMeta(ItemStack itemStack) {
        return new ItemBuilder(itemStack)
                .setUnbreakable(true)
                .addRole(ItemBuilder.ROLE_PERSISTENT_EQUIPMENT)
                .build();
    }
}
