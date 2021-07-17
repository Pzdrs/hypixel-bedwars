package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.events.BedwarsTeamEliminationEvent;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsTeamEliminationListener implements Listener {
    private Bedwars plugin;

    public BedwarsTeamEliminationListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTeamElimination(BedwarsTeamEliminationEvent event) {
        Utils.inGameBroadcast(Component.newline()
                .append(Component.text("TEAM ELIMINATED > ", Style.style(TextDecoration.BOLD)))
                .append(event.getTeam().getTeamColor().getDisplay())
                .append(Component.text(" has been eliminated!", NamedTextColor.RED))
                .append(Component.newline()));
    }
}
