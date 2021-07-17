package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsPlayerKillListener implements Listener {
    private Bedwars plugin;

    public BedwarsPlayerKillListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerKill(BedwarsPlayerKillEvent event) {
        if (event.isFinalKill()) {
            event.getBKiller().getStatistics().setFinalKills(event.getBKiller().getStatistics().getFinalKills() + 1);
            event.getBKiller().getStatistics().setDeaths(event.getBKiller().getStatistics().getDeaths() + 1);
            event.getBPlayer().getTeam().getPlayers().put(event.getBPlayer(), true);
        } else {
            event.getBKiller().getStatistics().setKills(event.getBKiller().getStatistics().getKills());
            event.getBKiller().getStatistics().setDeaths(event.getBKiller().getStatistics().getDeaths() + 1);
        }
    }
}
