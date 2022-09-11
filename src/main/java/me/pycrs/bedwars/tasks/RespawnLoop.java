package me.pycrs.bedwars.tasks;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.events.BedwarsPlayerBeginRespawnEvent;
import me.pycrs.bedwars.events.BedwarsPlayerRespawnEvent;
import me.pycrs.bedwars.util.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class RespawnLoop extends BedwarsRunnable {
    private final BedwarsPlayerBeginRespawnEvent event;
    public static AtomicInteger timer;

    public RespawnLoop(Bedwars plugin, BedwarsPlayerBeginRespawnEvent event) {
        super(plugin);
        this.event = event;
        timer = new AtomicInteger(5);
    }

    @Override
    public void run() {
        if (timer.get() == 0) {
            this.cancel();
            return;
        }
        Player player = event.getPlayer();
        player.showTitle(Title.title(
                Component.text("YOU DIED!", NamedTextColor.RED),
                Component.text(Utils.color("&eYou will respawn in &c" + timer.get() + " &eseconds!")),
                Title.Times.of(Duration.ZERO, Duration.ofMillis(1500), Duration.ZERO)));
        player.sendMessage(Component.text(Utils.color("&eYou will respawn in &c" + timer.getAndDecrement() + " &eseconds!")));
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        Bukkit.getPluginManager().callEvent(new BedwarsPlayerRespawnEvent(plugin, event.getBedwarsPlayer()));
    }
}
