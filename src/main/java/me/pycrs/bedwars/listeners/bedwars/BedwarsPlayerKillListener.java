package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class BedwarsPlayerKillListener implements Listener {
    private Bedwars plugin;

    public BedwarsPlayerKillListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerKill(BedwarsPlayerKillEvent event) {
        // Statistics
        if (event.isFinalKill()) {
            event.getBedwarsKiller().getStatistics().setFinalKills(event.getBedwarsKiller().getStatistics().getFinalKills() + 1);
        } else {
            event.getBedwarsKiller().getStatistics().setKills(event.getBedwarsKiller().getStatistics().getKills() + 1);
        }
        event.getBedwarsPlayer().getStatistics().setDeaths(event.getBedwarsPlayer().getStatistics().getDeaths() + 1);

        // Player drops
        event.getResources().forEach((material, amount) -> {
            BWCurrency currency = BWCurrency.fromType(material);
            if (currency != null) {
                event.getKiller().getInventory().addItem(
                        new ItemBuilder(material, amount)
                                .setPersistentData(Generator.RESOURCE_MARKER_KEY, PersistentDataType.INTEGER, Generator.ResourceState.PICKED_UP.state())
                                .build()
                );
                event.getKiller().sendMessage(Component.text(String.format("+%d ", amount), currency.getColor()).append(Component.text(currency.capitalize())));
            }
        });
    }
}
