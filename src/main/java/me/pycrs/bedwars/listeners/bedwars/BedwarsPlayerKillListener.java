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
        // TODO: 6/19/2021 remove from team
        if (event.isFinalKill()) {
            event.getBKiller().setFinalKills(event.getBKiller().getFinalKills() + 1);
            event.getBPlayer().getTeam().getPlayers().remove(event.getBPlayer());
        } else {
            event.getBKiller().setKills(event.getBKiller().getKills() + 1);
        }
    }
}
