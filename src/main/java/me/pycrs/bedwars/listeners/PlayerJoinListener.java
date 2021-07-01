package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.BedwarsPlayer;
import me.pycrs.bedwars.util.Utils;
import me.pycrs.bedwars.tasks.LobbyLoop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerJoinListener implements Listener {
    private Bedwars plugin;

    public PlayerJoinListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BedwarsPlayer bedwarsPlayer = BedwarsPlayer.toBPlayer(player);

        // Set player's display name
        player.displayName(Component.text(event.getPlayer().getName(), NamedTextColor.GRAY));

        if (Bedwars.isGameInProgress()) {
            event.joinMessage(Component.text(player.getName(), BedwarsPlayer.toBPlayer(player).getTeam().getTeamColor().getColor())
                    .append(Component.text(" reconnected", NamedTextColor.GRAY)));
            // TODO: 6/20/2021 death and respawn
            bedwarsPlayer.setSpectator(true);
        } else {
            // New join messages
            event.joinMessage(player.displayName()
                    .append(Component.text(" has joined ", NamedTextColor.YELLOW))
                    .append(Component.text(
                            Utils.color("&e(&b" + Bukkit.getOnlinePlayers().size() + "&e/&b" +
                                    Bedwars.getMode().getTeamSize() * Bedwars.getMode().getAmountOfTeams() + "&e)!"))));
            
            player.teleport(plugin.getMap().getLobbySpawnExact());

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
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

            player.setScoreboard(scoreboard);

            // If enough players, start the countdown
            if (Bukkit.getOnlinePlayers().size() >= Bedwars.getMode().getMinPlayers()) {
                if (!LobbyLoop.isCountingDown()) plugin.startLobbyCountdown();
                if (Bukkit.getOnlinePlayers().size() == Bedwars.getMode().getTeamSize() * Bedwars.getMode().getAmountOfTeams())
                    LobbyLoop.timer.set(10);
                player.sendMessage(
                        Component.text(Utils.color("&eThe game is starting in&b " + LobbyLoop.timer + " &e" + (LobbyLoop.timer.get() <= 1 ? "second!" : "seconds!"))));
            }
        }
    }
}
