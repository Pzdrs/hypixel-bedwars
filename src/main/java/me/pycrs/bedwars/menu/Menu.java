package me.pycrs.bedwars.menu;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.menu.button.MenuButton;
import me.pycrs.bedwars.menu.shops.Shop;
import me.pycrs.bedwars.util.InventoryUtils;
import me.pycrs.bedwars.util.MenuUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Menu implements InventoryHolder, Listener {
    protected Inventory inventory;
    protected Player player;
    private Map<String, MenuButton> buttons;

    public Menu(Player player) {
        this.player = player;
        this.buttons = new HashMap<>();
        Bedwars.getInstance().getServer().getPluginManager().registerEvents(this, Bedwars.getInstance());
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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof Menu)) return;
        event.setCancelled(true);
        Shop shop = (Shop) event.getInventory().getHolder();
        if (event.getClickedInventory() != null) {
            // Ignore clicks in player's own inventory, null items and roleless items
            if (!(event.getClickedInventory().getHolder() instanceof Menu) || event.getCurrentItem() == null || !MenuUtils.hasRole(event.getCurrentItem()))
                return;
            // Check if the player has clicked on a custom menu button
            Optional<String> potentialMenuButtonID = InventoryUtils
                    .getPersistentData(event.getCurrentItem(), new NamespacedKey(Bedwars.getInstance(), "menuButtonID"), PersistentDataType.STRING);
            if (potentialMenuButtonID.isPresent()) {
                Menu menu = (Menu) event.getInventory().getHolder();
                menu.getButtons().get(potentialMenuButtonID.get()).getHandler().handle();
                return;
            }
            shop.handle(event);
        } else {
            // Category cycling
            if (event.getClick().isLeftClick()) {
                shop.cycleCategory(Shop.CategoryCycleDirection.LEFT);
            } else if (event.getClick().isRightClick()) {
                shop.cycleCategory(Shop.CategoryCycleDirection.RIGHT);
            }
        }


    }
}
