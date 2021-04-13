package me.pycrs.bedwarsrecoded.inventory.menus;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;
    protected Player player;

    public Menu(Player player) {
        this.player = player;
    }

    protected abstract Component getName();

    protected abstract int getSize();

    public abstract void handle(InventoryClickEvent event);

    protected abstract void setContent();

    public void open() {
        this.inventory = Bukkit.createInventory(this, getSize(), getName());
        setContent();
        player.openInventory(inventory);
    }

    @Override
    public final @NotNull Inventory getInventory() {
        return inventory;
    }
}
