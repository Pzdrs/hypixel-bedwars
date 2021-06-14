package me.pycrs.bedwars.inventory.menu;

import me.pycrs.bedwars.inventory.menu.button.MenuButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;
    protected Player player;
    private Map<String, MenuButton> buttons;

    public Menu(Player player) {
        this.player = player;
        this.buttons = new HashMap<>();
    }

    protected abstract Component getTitle();

    protected abstract int getSize();

    public abstract void handle(InventoryClickEvent event);

    protected abstract void setContent();

    public void open() {
        this.inventory = Bukkit.createInventory(this, getSize(), getTitle());
        setContent();
        player.openInventory(inventory);
    }

    public final Map<String, MenuButton> getButtons() {
        return buttons;
    }

    @Override
    public final @NotNull Inventory getInventory() {
        return inventory;
    }
}
