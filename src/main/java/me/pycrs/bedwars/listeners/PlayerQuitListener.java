package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Mode;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsGameEndEvent;
import me.pycrs.bedwars.scoreboard.LobbyScoreboard;
import me.pycrs.bedwars.tasks.LobbyLoop;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Duration;
import java.util.Optional;

public class PlayerQuitListener extends BaseListener<Bedwars> {
    public PlayerQuitListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        int actualPlayerAmount = Bukkit.getOnlinePlayers().size() - 1;

        switch (Bedwars.getGameStage()) {
            case LOBBY_WAITING -> {
                onLobbyQuit(event, player);
                LobbyScoreboard.get().removePlayer(player);
                // A tiny delay is needed because when Bukkit.getOnlinePlayers() is called here, the player disconnecting hasn't reflected on the list yet
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    LobbyScoreboard.get().getBody().updateLine("player_count");
                    LobbyScoreboard.get().getBody().updateLine("countdown");
                }, 1);
            }
            case LOBBY_COUNTDOWN -> {
                onLobbyQuit(event, player);
                // If there is not enough people for the game to start, cancel the countdown
                if (actualPlayerAmount < Settings.mode.getMinPlayers()) {
                    LobbyLoop.stop();
                    Bukkit.getServer().playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT, Sound.Source.BLOCK, 1f, 1f));
                    Utils.inGameBroadcast(Component.text("We don't have enough players! Start cancelled.", NamedTextColor.RED));
                    Bukkit.getServer().showTitle(Title.title(Component.text("Waiting for more players...", NamedTextColor.RED), Component.text().asComponent(),
                            Title.Times.of(Duration.ZERO, Duration.ofMillis(2000), Duration.ZERO)));
                }
                // A tiny delay is needed because when Bukkit.getOnlinePlayers() is called here, the player disconnecting hasn't reflected on the list yet
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    LobbyScoreboard.get().getBody().updateLine("player_count");
                }, 1);
            }
            case GAME_IN_PROGRESS -> BedwarsPlayer.of(player).ifPresent(bedwarsPlayer -> {
                BedwarsTeam team = bedwarsPlayer.getTeam();
                // We don't care when spectators disconnect
                if (bedwarsPlayer.isEliminated()) {
                    event.quitMessage(null);
                    return;
                }

                // Quit message
                event.quitMessage(
                        Component.text(player.getName(), team.getTeamColor().getTextColor())
                                .append(Component.text(" disconnected.", NamedTextColor.GRAY))
                );

                // If everyone leaves, the game will end
                if (actualPlayerAmount == 0) {
                    Bukkit.getPluginManager().callEvent(new BedwarsGameEndEvent(plugin, BedwarsGameEndEvent.Result.EVERYONE_LEFT));
                }
                // If someone leaves with their bed being gone, they are eliminated
                // Also, if the game is in SOLO mode and the person disconnects, there is no one else in the team, so the team gets eliminated
                if (!team.hasBed() || (Settings.mode == Mode.SOLO && Settings.SOLO_WIPE_EMPTY_TEAMS))
                    // A tiny delay needs to be added so that the quit message is displayed before a potential team elimination message
                    Bukkit.getScheduler().runTaskLater(plugin, () -> team.eliminatePlayer(bedwarsPlayer), 1);
            });
        }
    }

    private void onLobbyQuit(PlayerQuitEvent event, Player player) {
        // Quit message
        event.quitMessage(player.displayName()
                .append(Component.text(" has quit! ", NamedTextColor.YELLOW)));
        LobbyScoreboard.get().removePlayer(player);
    }
}
