package me.pycrs.bedwars.scoreboard;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.scoreboard.body.ScoreboardBody;
import me.pycrs.bedwars.scoreboard.body.line.DynamicScoreboardLine;
import me.pycrs.bedwars.scoreboard.body.line.SimpleScoreboardLine;
import me.pycrs.bedwars.tasks.LobbyLoop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;

import java.time.LocalDate;

public class LobbyScoreboard extends GlobalBedwarsScoreboard {
    private static LobbyScoreboard INSTANCE;

    public static LobbyScoreboard get() {
        if (INSTANCE == null) INSTANCE = new LobbyScoreboard();
        return INSTANCE;
    }

    public LobbyScoreboard() {
        super("lobby", Component.text("BEDWARS", NamedTextColor.YELLOW, TextDecoration.BOLD), ScoreboardBody.builder()
                .line(new SimpleScoreboardLine(Component.text()
                        .append(Component.text(LocalDate.now().format(Settings.SCOREBOARD_DATE_FORMAT), NamedTextColor.GRAY))
                        .append(Component.space())
                        .append(Component.text("m427V", NamedTextColor.DARK_GRAY)).build()
                ))
                .newline()
                .line(new SimpleScoreboardLine(Component.text("Map: ")
                        .append(Component.text(Bedwars.getMap().getName(), NamedTextColor.GREEN))
                ))
                .dynamicLine(new DynamicScoreboardLine("player_count", Component.text("Players:"), Component.space(), () -> Component.text()
                        .append(Component.text(Bukkit.getOnlinePlayers().size(), NamedTextColor.GREEN))
                        .append(Component.text("/"))
                        .append(Component.text(Settings.mode.getTeamSize() * Settings.mode.getAmountOfTeams(), NamedTextColor.GREEN)).build()
                ))
                .newline()
                .dynamicLine(new DynamicScoreboardLine("countdown", () -> {
                            if (Bedwars.getGameStage().isLobbyCountingDown()) {
                                return Component.text("Starting in ")
                                        .append(Component.text(LobbyLoop.timer.get() + "s", NamedTextColor.GREEN));
                            } else return Component.text("Waiting...");
                        })
                )
                .newline()
                .line(new SimpleScoreboardLine(Component.text("Mode: ")
                        .append(Component.text(Settings.mode.getDisplayName(), NamedTextColor.GREEN))
                ))
                .line(new SimpleScoreboardLine(Component.text("Version: ")
                        .append(Component.text("v1.6", NamedTextColor.GREEN))
                ))
                .newline()
                .line(new SimpleScoreboardLine(Component.text("www.hypixel.net", NamedTextColor.YELLOW)))
                .build()
        );
    }
}
