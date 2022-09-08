package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import me.pycrs.bedwars.events.BedwarsPlayerBeginRespawnEvent;
import me.pycrs.bedwars.listeners.BaseListener;
import me.pycrs.bedwars.tasks.RespawnLoop;
import me.pycrs.bedwars.util.Utils;
import me.pycrs.bedwars.events.BedwarsPlayerRespawnEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class BedwarsPlayerRespawnListener extends BaseListener<Bedwars> {
    public BedwarsPlayerRespawnListener(Bedwars plugin) {
        super(plugin);
    }


    @EventHandler
    public void onBeginPlayerRespawn(BedwarsPlayerBeginRespawnEvent event) {
        new RespawnLoop(plugin, event).runTaskTimer(plugin, 0, 20);
    }

    @EventHandler
    public void onPlayerRespawn(BedwarsPlayerRespawnEvent event) {
        Player player = event.getPlayer();
        BedwarsPlayer bedwarsPlayer = event.getBedwarsPlayer();

        bedwarsPlayer.setSpectator(false);
        bedwarsPlayer.getEquipment().updateArmor(false);
        bedwarsPlayer.getEquipment().updateEquipment();
        bedwarsPlayer.showLevel();
        bedwarsPlayer.teleportToBase();
        player.setInvisible(false);
        player.sendMessage(Component.text("You have respawned!", NamedTextColor.YELLOW));
        player.showTitle(Title.title(
                Component.text("RESPAWNED!", NamedTextColor.GREEN),
                Component.empty(),
                Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)
        ));
    }
}
