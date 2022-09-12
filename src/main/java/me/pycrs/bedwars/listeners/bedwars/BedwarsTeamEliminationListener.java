package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayerList;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.entities.team.BedwarsTeamList;
import me.pycrs.bedwars.events.BedwarsGameEndEvent;
import me.pycrs.bedwars.events.BedwarsTeamEliminationEvent;
import me.pycrs.bedwars.listeners.BaseListener;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.util.List;

public class BedwarsTeamEliminationListener extends BaseListener<Bedwars> {
    public BedwarsTeamEliminationListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onTeamElimination(BedwarsTeamEliminationEvent event) {
        event.getTeam().setEliminated(true);
        Utils.inGameBroadcast(Component.newline()
                .append(Component.text("TEAM ELIMINATED > ", Style.style(TextDecoration.BOLD)))
                .append(event.getTeam().getTeamColor().getDisplay())
                .append(Component.text(" has been eliminated!", NamedTextColor.RED))
                .append(Component.newline()));
        BedwarsPlayerList.getList().forEach(bedwarsPlayer -> bedwarsPlayer.getScoreboard().getBody().updateLine(event.getTeam().getTeamColor().name()));
        List<BedwarsTeam> teams = BedwarsTeamList.getList().alive();
        if (teams.size() == 1)
            Bukkit.getServer().getPluginManager().callEvent(new BedwarsGameEndEvent(plugin, BedwarsGameEndEvent.Result.NORMAL, teams.get(0)));
    }
}
