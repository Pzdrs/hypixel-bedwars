package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsBedBreakEvent;
import me.pycrs.bedwars.events.BedwarsGameStartEvent;
import me.pycrs.bedwars.tasks.GameLoop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsGameStartListener implements Listener {
    private Bedwars plugin;

    public BedwarsGameStartListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameStart(BedwarsGameStartEvent event) {
        BedwarsTeam.distributePlayers();
        Bedwars.gameLoop = new GameLoop(plugin);
        Bedwars.gameLoop.runTaskTimer(plugin, 0, 20);
    }
}
