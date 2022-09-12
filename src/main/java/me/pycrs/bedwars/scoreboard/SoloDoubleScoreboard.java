package me.pycrs.bedwars.scoreboard;

import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.entities.team.BedwarsTeamList;
import me.pycrs.bedwars.entities.team.TeamColor;
import me.pycrs.bedwars.scoreboard.body.ScoreboardBody;
import me.pycrs.bedwars.scoreboard.body.line.DynamicScoreboardLine;
import me.pycrs.bedwars.scoreboard.body.line.ScoreboardLine;
import me.pycrs.bedwars.scoreboard.body.line.SimpleScoreboardLine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.time.LocalDate;

public class SoloDoubleScoreboard extends PerPlayerScoreboard {
    public SoloDoubleScoreboard(BedwarsPlayer bedwarsPlayer) {
        super(bedwarsPlayer.getPlayer().getName(), bedwarsPlayer.getPlayer(),
                Component.text("BEDWARS", NamedTextColor.YELLOW, TextDecoration.BOLD), ScoreboardBody.builder()
                        .line(new SimpleScoreboardLine(Component.text()
                                .append(Component.text(LocalDate.now().format(Settings.SCOREBOARD_DATE_FORMAT), NamedTextColor.GRAY))
                                .append(Component.space())
                                .append(Component.text("m427V", NamedTextColor.DARK_GRAY)).build()
                        ))
                        .newline()
                        .line(new SimpleScoreboardLine(Component.text("event")))
                        .newline()
                        .lines(() -> {
                            BedwarsTeamList teams = BedwarsTeamList.getList();
                            ScoreboardLine[] lines = new ScoreboardLine[teams.size()];
                            for (int i = 0; i < lines.length; i++) {
                                BedwarsTeam team = teams.get(i);
                                lines[i] = new DynamicScoreboardLine(team.getTeamColor().name(),
                                        team.getTeamColor().getScoreboardRepresentation(),
                                        Component.text(": "),
                                        () -> team.getScoreboardStatus(bedwarsPlayer));
                            }
                            return lines;
                        })
                        .newline()
                        .line(new SimpleScoreboardLine(Component.text("www.hypixel.net", NamedTextColor.YELLOW)))
                        .build());
    }
}
