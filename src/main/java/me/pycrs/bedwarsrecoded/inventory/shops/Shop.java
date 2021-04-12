package me.pycrs.bedwarsrecoded.inventory.shops;

import javafx.util.Pair;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class Shop implements InventoryHolder {
    private Inventory inventory;
    protected Map<ShopCategory, Boolean> categories;
    private Player player;
    protected List<ShopItem> items;

    public Shop() {
        this.items = new ArrayList<>();
        this.categories = new LinkedHashMap<>();
    }

    private void injectItems() {
        if (categorical()) {
            int index = 0;
            inventory.setItem(9, getCategoryDiode(false));
            inventory.setItem(17, getCategoryDiode(false));
            for (Map.Entry<ShopCategory, Boolean> category : categories.entrySet()) {
                inventory.setItem(index, removeLoreIfActive(category.getKey().getPreview(), category.getValue()));
                inventory.setItem(index + 9, getCategoryDiode(category.getValue()));
                if (category.getValue()) {
                    for (int i = 0; i < category.getKey().getItems().size(); i++) {
                        ShopItem item = category.getKey().getItems().get(i);
                        ItemStack itemStack = item.getPreview();
                        ItemMeta meta = itemStack.getItemMeta();

                        meta.displayName(Component.text((canBeBought(item.getCost()) ? ChatColor.GREEN : ChatColor.RED) + Utils.materialToFriendlyName(itemStack.getType())));

                        List<Component> lore = new ArrayList<>(Objects.requireNonNull(meta.lore()));
                        lore.add(Component.text(Utils.color(canBeBought(item.getCost()) ? "&eClick to purchase!" : "&cYou don't have enough " + WordUtils.capitalize(item.getCost().getKey().name().toLowerCase()) + "!")));
                        meta.lore(lore);

                        itemStack.setItemMeta(meta);
                        inventory.setItem(index + i + 18, itemStack);
                    }
                }
                index++;
            }
        } else {
            items.forEach(shopItem -> inventory.addItem(shopItem.getPreview()));
        }
    }

    public abstract void handlePurchase(InventoryClickEvent event);

    private ItemStack removeLoreIfActive(ItemStack preview, boolean active) {
        if (active) preview.lore(new ArrayList<>());
        return preview;
    }

    private ItemStack getCategoryDiode(boolean active) {
        ItemStack diode = new ItemStack(active ? Material.GREEN_STAINED_GLASS_PANE : Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = diode.getItemMeta();

        // FIXME: 4/12/2021 wrong unicode, figure out how to use surrogate pairs
        meta.displayName(Component.text(Utils.color("&8\u2191 &7Categories")));
        meta.lore(new ArrayList<>(Collections.singletonList(Component.text(Utils.color("&8\u2193 &7Items")))));

        diode.setItemMeta(meta);
        return diode;
    }

    private boolean canBeBought(Pair<BWCurrency, Integer> cost) {
        HashMap<Material, Integer> resources = new HashMap<>();
        for (ItemStack content : player.getInventory().getContents()) {
            if (content == null) break;
            if (resources.containsKey(content.getType())) {
                resources.put(content.getType(), resources.get(content.getType()) + content.getAmount());
                continue;
            }
            resources.put(content.getType(), content.getAmount());
        }
        return resources.get(cost.getKey().getMaterial()) != null && resources.get(cost.getKey().getMaterial()) >= cost.getValue();
    }

    public abstract boolean categorical();

    public abstract int getSize();

    public void setShopItems() {

    }

    public void setCategories() {

    }

    public final void show(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, getSize());
        setShopItems();
        setCategories();
        injectItems();
        player.openInventory(inventory);
    }

    @Override
    public final @NotNull Inventory getInventory() {
        return inventory;
    }
}
