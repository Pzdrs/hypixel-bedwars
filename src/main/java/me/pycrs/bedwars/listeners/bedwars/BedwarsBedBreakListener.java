package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.events.BedwarsBedBreakEvent;
import me.pycrs.bedwars.listeners.BaseListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsBedBreakListener extends BaseListener<Bedwars> {
    public BedwarsBedBreakListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onBedBreak(BedwarsBedBreakEvent event) {
        if (event.getTeam().isPartOfTeam(event.getBedwarsPlayer())) {
            event.getBedwarsPlayer().getPlayer().sendMessage(Component.text("You can't destroy your own bed!", NamedTextColor.RED));
            event.setCancelled(true);
        } else {
            // FIXME: 6/17/2021 for debugging and keeping the map in decent state the bed itself doesnt break
            event.setCancelled(true);
            event.getTeam().destroyBed(event.getBedwarsPlayer());
        }
    }
}
