package me.pycrs.bedwars.menu.button;

import org.bukkit.inventory.ItemStack;

public class MenuButton {
    private ItemStack itemStack;
    private MenuButtonHandler handler;

    public MenuButton(ItemStack itemStack, MenuButtonHandler handler) {
        this.itemStack = itemStack;
        this.handler = handler;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public MenuButtonHandler getHandler() {
        return handler;
    }
}
