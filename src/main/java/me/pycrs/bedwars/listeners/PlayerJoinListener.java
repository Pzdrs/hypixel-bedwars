package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.scoreboard.LobbyScoreboard;
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
        player.teleport(Bedwars.getMap().getLobbySpawnExact());

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
    }

    private void onLobbyJoin(PlayerJoinEvent event, Player player) {
        // New join messages
        event.joinMessage(player.displayName()
                .append(Component.text(" has joined ", NamedTextColor.YELLOW))
                .append(Component.text(
                        Utils.color("&e(&b" + Bukkit.getOnlinePlayers().size() + "&e/&b" +
                                Bedwars.getMode().getTeamSize() * Bedwars.getMode().getAmountOfTeams() + "&e)!"))));
        // Display name
        player.displayName(player.displayName().color(NamedTextColor.GRAY));
        BedwarsPlayer.PlayerListName.LOBBY.apply(player);
        // If enough players, start the countdown
        if (Bedwars.getMode().isEnough(Bukkit.getOnlinePlayers().size())) {
            // If enough players are online, start the countdown
            LobbyLoop.start(plugin);
            // If we have a full lobby, cut the countdown
            if (Bedwars.getMode().isFull(Bukkit.getOnlinePlayers().size()))
                LobbyLoop.timer.set(Settings.FULL_LOBBY_COUNTDOWN);
        }
        LobbyScoreboard.get().getBody().updateLine("player_count");
        LobbyScoreboard.get().addPlayer(player);
    }
}
