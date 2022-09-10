package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.tasks.LobbyLoop;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

public class PlayerJoinListener extends BaseListener<Bedwars> {
    public PlayerJoinListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Utils.sanitizePlayer(player);
        player.teleport(plugin.getMap().getLobbySpawnExact());

        switch (Bedwars.getGameStage()) {
            case LOBBY_WAITING -> onLobbyJoin(event, player);
            case LOBBY_COUNTDOWN -> {
                onLobbyJoin(event, player);
                player.sendMessage(
                        Component.text(Utils.color("&eThe game is starting in&b " + LobbyLoop.timer + " &e" + (LobbyLoop.timer.get() <= 1 ? "second!" : "seconds!"))));
            }
            case GAME_IN_PROGRESS -> {
                Optional<BedwarsPlayer> potentialPlayer = BedwarsPlayer.of(player);
                if (potentialPlayer.isPresent()) {
                    BedwarsPlayer bedwarsPlayer = potentialPlayer.get();
                    bedwarsPlayer.updatePlayerObject(player);
                    BedwarsTeam team = bedwarsPlayer.getTeam();
                    if (team.isEliminated()) {
                        bedwarsPlayer.setSpectator(true);
                        player.sendMessage(
                                Component.newline()
                                        .append(Component.text("To leave Bed Wars, type /lobby", NamedTextColor.YELLOW, TextDecoration.BOLD))
                                        .append(Component.newline())
                                        .append(Component.text("Your bed was destroyed so you are a spectator!", NamedTextColor.YELLOW))
                        );
                    } else {
                        event.joinMessage(
                                Component.text(player.getName(), team.getTeamColor().getTextColor())
                                        .append(Component.text(" reconnected.", NamedTextColor.GRAY))
                        );
                        // A tiny delay is needed so that the chat behaviour matches Hypixel
                        Bukkit.getScheduler().runTaskLater(plugin, bedwarsPlayer::killCauseless, 1);
                        return;
                    }
                }
                event.joinMessage(null);
            }
        }


    /*            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective lobby = scoreboard.registerNewObjective("lobby", "dummy", Component.text("BEDWARS",
                    Style.style(NamedTextColor.YELLOW, TextDecoration.BOLD)));

            lobby.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score info = lobby.getScore(Utils.color("&704/06/21  &8m230G"));
            info.setScore(11);

            lobby.getScore("   ").setScore(10);

            Score map = lobby.getScore(Utils.color("Map: &a" + plugin.getMap().getName()));
            map.setScore(9);

            Score players = lobby.getScore(Utils.color("Players: &a0/8"));
            players.setScore(8);

            lobby.getScore("  ").setScore(7);

            Score startingIn = lobby.getScore(Utils.color("Waiting..."));
            startingIn.setScore(6);

            lobby.getScore(" ").setScore(5);

            Score mode = lobby.getScore(Utils.color("Mode: &aSolo"));
            mode.setScore(4);

            Score version = lobby.getScore(Utils.color("Version: &7v1.6"));
            version.setScore(3);

            lobby.getScore("").setScore(2);

            Score link = lobby.getScore(Utils.color("&ewww.hypixel.net"));
            link.setScore(1);

            player.setScoreboard(scoreboard);*/
    }

    private void onLobbyJoin(PlayerJoinEvent event, Player player) {
        // New join messages
        event.joinMessage(player.displayName()
                .append(Component.text(" has joined ", NamedTextColor.YELLOW))
                .append(Component.text(
                        Utils.color("&e(&b" + Bukkit.getOnlinePlayers().size() + "&e/&b" +
                                Settings.mode.getTeamSize() * Settings.mode.getAmountOfTeams() + "&e)!"))));
        // Display name
        player.displayName(Component.text(event.getPlayer().getName(), NamedTextColor.GRAY));
        // If enough players, start the countdown
        if (Bukkit.getOnlinePlayers().size() >= Settings.mode.getMinPlayers()) {
            // If enough players are online, start the countdown
            if (!Bedwars.getGameStage().isLobbyCountingDown()) Bedwars.startLobbyCountdown();
            // If we have a full lobby, cut the countdown
            if (Bukkit.getOnlinePlayers().size() == Settings.mode.getTeamSize() * Settings.mode.getAmountOfTeams())
                LobbyLoop.timer.set(Settings.FULL_LOBBY_COUNTDOWN);
        }
    }
}
