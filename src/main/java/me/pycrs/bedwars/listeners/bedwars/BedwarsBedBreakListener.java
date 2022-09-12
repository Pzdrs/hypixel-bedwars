package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayerList;
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
        // FIXME: 6/17/2021 for debugging and keeping the map in decent state the bed itself doesnt break
        event.setCancelled(true);
        event.getTeam().destroyBed(event.getBedwarsPlayer());
        BedwarsPlayerList.getList().forEach(bedwarsPlayer -> bedwarsPlayer.getScoreboard().getBody().updateLine(event.getTeam().getTeamColor().name()));
    }
}
