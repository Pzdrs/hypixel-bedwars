package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsGameEndEvent;
import me.pycrs.bedwars.events.BedwarsGameStartEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.tasks.GameLoop;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsGameEndListener implements Listener {
    private Bedwars plugin;

    public BedwarsGameEndListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameEnd(BedwarsGameEndEvent event) {

    }
}
