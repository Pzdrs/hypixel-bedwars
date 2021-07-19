package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsGameEndEvent;
import me.pycrs.bedwars.events.BedwarsGameStartEvent;
import me.pycrs.bedwars.generators.Generator;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Duration;

public class BedwarsGameEndListener implements Listener {
    private Bedwars plugin;

    public BedwarsGameEndListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGameEnd(BedwarsGameEndEvent event) {
        if (event.getResult() == BedwarsGameEndEvent.Result.NORMAL) {
            plugin.getTeams().forEach(team -> {
                // Title either announcing your victory or your loss
                team.broadcastTitle(Title.title(
                        (team.getTeamColor() == event.getTeam().getTeamColor()) ?
                                Component.text("VICTORY!", NamedTextColor.GOLD, TextDecoration.BOLD) :
                                Component.text("GAME OVER!", NamedTextColor.RED, TextDecoration.BOLD),
                        Component.empty(),
                        Title.Times.of(Duration.ZERO, Duration.ofSeconds(5), Duration.ZERO)));
                // Game summary
                // TODO: 7/19/2021 The unicode symbol is subject to change
                team.broadcastMessage(Component.empty()
                        .append(Utils.nAmountOfSymbols("\u25ac", 80).color(NamedTextColor.GREEN)).append(Component.newline())
                        .append(Utils.nAmountOfSymbols(" ", 34)
                                .append(Component.text("Bed Wars", Style.style(TextDecoration.BOLD)))).append(Component.newline())
                        .append(team.getVictoryTeamMembersList())
                        .append(Component.newline())
                        .append(Utils.nAmountOfSymbols("\u25ac", 80).color(NamedTextColor.GREEN)));
            });
        }
    }
}
