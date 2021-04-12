package me.pycrs.bedwarsrecoded.inventory.shops;

import javafx.util.Pair;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
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
    protected LinkedList<ShopCategory> categories;
    private ShopCategory activeCategory;
    private Player player;
    protected List<ShopItem> items;

    public Shop() {
        this.items = new ArrayList<>();
        this.categories = new LinkedList<>();
    }

    public void changeCategory(String id) {
        System.out.println(id);
        this.activeCategory = categories
                .stream()
                .filter(category -> category.getId().equals(id))
                .findFirst().orElse(null);
    }

    private void injectItems() {
        if (categorical()) {
            inventory.setItem(9, getCategoryDiode(false));
            inventory.setItem(17, getCategoryDiode(false));
            for (int i = 0; i < categories.size(); i++) {
                ShopCategory category = categories.get(i);
                boolean active = activeCategory.getId().equals(category.getId());
                inventory.setItem(i, removeLoreIfActive(category.getPreview(), active));
                inventory.setItem(i + 9, getCategoryDiode(active));
                if (active) {
                    int lastItemPosition = 18;
                    for (int j = 0; j < category.getItems().size(); j++) {
                        // Rendering more than 21 items on one page isn't possible
                        if (j > 20) break;

                        ShopItem item = category.getItems().get(j);
                        ItemStack itemStack = item.getPreview();
                        ItemMeta meta = itemStack.getItemMeta();

                        meta.displayName(Component.text((canBeBought(item.getCost()) ? ChatColor.GREEN : ChatColor.RED) + Utils.materialToFriendlyName(itemStack.getType())));

                        List<Component> lore = new ArrayList<>(Objects.requireNonNull(meta.lore()));
                        lore.add(Component.text(Utils.color(canBeBought(item.getCost()) ? "&eClick to purchase!" : "&cYou don't have enough " + WordUtils.capitalize(item.getCost().getKey().name().toLowerCase()) + "!")));
                        meta.lore(lore);

                        itemStack.setItemMeta(meta);

                        int itemPosition = lastItemPosition + (j == 7 || j == 14 ? 3 : 1);
                        inventory.setItem(itemPosition, itemStack);
                        lastItemPosition = itemPosition;
                    }
                }
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

    private void render() {
        setShopItems();
        setCategories();
        if (activeCategory == null) this.activeCategory = categories.get(0);
        this.inventory = Bukkit.createInventory(this, getSize(), Component.text(activeCategory.getName()));
        injectItems();
        if (player != null) player.updateInventory();
    }

    public final void show(Player player) {
        this.player = player;
        render();
        player.openInventory(inventory);
    }

    @Override
    public final @NotNull Inventory getInventory() {
        return inventory;
    }
}
