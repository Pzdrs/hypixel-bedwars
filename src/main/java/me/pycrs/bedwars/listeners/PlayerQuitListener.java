package me.pycrs.bedwars.listeners;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Mode;
import me.pycrs.bedwars.Settings;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.entities.team.BedwarsTeam;
import me.pycrs.bedwars.events.BedwarsGameEndEvent;
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

public class PlayerQuitListener extends BaseListener<Bedwars> {
    public PlayerQuitListener(Bedwars plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        int actualPlayerAmount = Bukkit.getOnlinePlayers().size() - 1;

        if (Bedwars.isGameInProgress()) {
            BedwarsPlayer bedwarsPlayer = BedwarsPlayer.toBedwarsPlayer(player);
            BedwarsTeam team = bedwarsPlayer.getTeam();
            // Quit message - can't just set edit the message, because it's sent too late, timing is the sole reason for this messy workaround
            event.quitMessage(null);
            Utils.inGameBroadcast(Component.text(player.getName(), BedwarsPlayer.toBedwarsPlayer(player).getTeam().getTeamColor().getTextColor())
                    .append(Component.text(" disconnected", NamedTextColor.GRAY)));

            // If everyone leaves, the game will end
            if (actualPlayerAmount == 0) {
                Bukkit.getPluginManager().callEvent(new BedwarsGameEndEvent(plugin, BedwarsGameEndEvent.Result.EVERYONE_LEFT));
            }
            // If someone leaves while their bed is gone, they are eliminated
            // Also, if the game is in SOLO mode and the person disconnects, there is no one else in the team, so the team gets eliminated
            if (!team.hasBed() || Settings.mode == Mode.SOLO) team.eliminatePlayer(bedwarsPlayer);
        } else {
            // Quit message
            event.quitMessage(player.displayName()
                    .append(Component.text(" has quit! ", NamedTextColor.YELLOW)));

            // Stop lobby countdown if not enough people anymore
            if (LobbyLoop.isCountingDown()) {
                if (actualPlayerAmount < Settings.mode.getMinPlayers()) {
                    plugin.getLobbyLoop().cancel();
                    plugin.getServer().playSound(Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT, Sound.Source.BLOCK, 1f, 1f));
                    Utils.inGameBroadcast(Component.text("We don't have enough players! Start cancelled.", NamedTextColor.RED));
                    Bukkit.getServer().showTitle(Title.title(Component.text("Waiting for more players...", NamedTextColor.RED), Component.text().asComponent(),
                            Title.Times.of(Duration.ZERO, Duration.ofMillis(2000), Duration.ZERO)));
                }
            }
        }
    }
}
