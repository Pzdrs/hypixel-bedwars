package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import me.pycrs.bedwars.events.BedwarsPlayerRespawnEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.listeners.BaseListener;
import me.pycrs.bedwars.menu.shops.items.dependency.BWCurrency;
import me.pycrs.bedwars.util.ItemBuilder;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class BedwarsPlayerKillListener extends BaseListener<Bedwars> {

    public BedwarsPlayerKillListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerKill(BedwarsPlayerKillEvent event) {
        // Death message
        event.setMessage(BedwarsPlayerKillEvent.DeathMessage.getMessage(event.getLastDamage().getCause(), event.getBedwarsPlayer(), event.getBedwarsKiller()));

        // Statistics
        if (event.isFinalKill()) {
            event.getBedwarsKiller().getStatistics().addFinalKill();
        } else {
            event.getBedwarsKiller().getStatistics().addKill();
        }

        // Common player death logic
        BedwarsPlayerDeathListener.onPlayerDeath(event.getBedwarsPlayer(), plugin, event, () -> {
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
        });

    }
}
