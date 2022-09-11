package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeamList;
import me.pycrs.bedwars.events.BedwarsGameEndEvent;
import me.pycrs.bedwars.listeners.BaseListener;
import me.pycrs.bedwars.tasks.GameLoop;
import me.pycrs.bedwars.tasks.InventoryWatcher;
import me.pycrs.bedwars.entities.player.BedwarsPlayerList;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.time.Duration;

public class BedwarsGameEndListener extends BaseListener<Bedwars> {
    public BedwarsGameEndListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onGameEnd(BedwarsGameEndEvent event) {
        if (event.getResult() == BedwarsGameEndEvent.Result.NORMAL || event.getResult() == BedwarsGameEndEvent.Result.GAME_END) {
            Bedwars.setGameStage(Bedwars.GameStage.GAME_FINISHED);
            GameLoop.stop();
            InventoryWatcher.stop();
            // Sort the players by kills
            BedwarsTeamList.getList().forEach(team -> {
                // Title either announcing your victory or your loss
                team.broadcastTitle(Title.title(
                        (team.getTeamColor() == event.getTeam().getTeamColor()) ?
                                Component.text("VICTORY!", NamedTextColor.GOLD, TextDecoration.BOLD) :
                                Component.text("GAME OVER!", NamedTextColor.RED, TextDecoration.BOLD),
                        Component.empty(),
                        Title.Times.of(Duration.ZERO, Duration.ofSeconds(5), Duration.ZERO)));
                // Game summary
                team.broadcastMessage(Component.empty()
                        .append(Utils.nAmountOfSymbols("\u25ac", 80).color(NamedTextColor.GREEN)).append(Component.newline())
                        .append(Component.newline())
                        .append(Utils.nAmountOfSymbols(" ", 34)
                                .append(Component.text("Bed Wars", Style.style(TextDecoration.BOLD)))).append(Component.newline())
                        .append(team.getVictoryTeamMembersList()).append(Component.newline())
                        .append(Component.newline()).append(Component.newline())
                        .append(eligibleOfPlacement(0) ?
                                Component.text(Utils.color("&e&l1st Killer &7-&r ")).append(BedwarsPlayerList.getList().sorted().get(0).getPlayer().displayName())
                                        .append(Component.text(" - " + BedwarsPlayerList.getList().sorted().get(0).getStatistics().getCombinedKills(), NamedTextColor.GRAY))
                                        .append(Component.newline()) :
                                Component.empty())
                        .append(eligibleOfPlacement(1) ?
                                Component.text(Utils.color("&6&l2nd Killer &7-&r ")).append(BedwarsPlayerList.getList().sorted().get(1).getPlayer().displayName())
                                        .append(Component.text(" - " + BedwarsPlayerList.getList().sorted().get(1).getStatistics().getCombinedKills(), NamedTextColor.GRAY))
                                        .append(Component.newline()) :
                                Component.empty())
                        .append(eligibleOfPlacement(2) ?
                                Component.text(Utils.color("&c&l3rd Killer &7-&r ")).append(BedwarsPlayerList.getList().sorted().get(2).getPlayer().displayName())
                                        .append(Component.text(" - " + BedwarsPlayerList.getList().sorted().get(2).getStatistics().getCombinedKills(), NamedTextColor.GRAY))
                                        .append(Component.newline()) :
                                Component.empty())
                        .append(Component.newline())
                        .append(Utils.nAmountOfSymbols("\u25ac", 80).color(NamedTextColor.GREEN)));
            });
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                //Bukkit.getOnlinePlayers().forEach(player -> player.kick(Component.text("The server is restarting...")));
                Bukkit.reload();
            }, plugin.getConfig().getInt("rebootDelay") * 20L);
        }
    }

    private boolean eligibleOfPlacement(int index) {
        try {
            BedwarsPlayer bedwarsPlayer = BedwarsPlayerList.getList().sorted().get(index);
            return bedwarsPlayer.getStatistics().getCombinedKills() != 0;
        } catch (IndexOutOfBoundsException exception) {
            return false;
        }
    }
}
