package me.pycrs.bedwars.listeners.bedwars;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.Utils;
import me.pycrs.bedwars.events.BedwarsPlayerDeathEvent;
import me.pycrs.bedwars.events.BedwarsPlayerKillEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BedwarsPlayerKillListener implements Listener {
    private Bedwars plugin;

    public BedwarsPlayerKillListener(Bedwars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerKill(BedwarsPlayerKillEvent event) {
        Component killMessage = event.getPlayer().getPlayer().displayName().color(event.getPlayer().getTeam().getTeamColor().getColor())
                .append(BedwarsPlayerDeathEvent.DeathCause.PLAYER_ATTACK.getRandomMessage())
                .append(event.getKiller().getPlayer().displayName().color(event.getKiller().getTeam().getTeamColor().getColor()))
                .append(Component.text(".", NamedTextColor.GRAY));
        if (event.isFinalKill()) {
            event.getKiller().setFinalKills(event.getKiller().getFinalKills() + 1);
            Utils.inGameBroadcast(killMessage.append(Component.text(" FINAL KILL!", Style.style(NamedTextColor.AQUA, TextDecoration.BOLD))));
            event.getPlayer().getPlayer().sendMessage(Component.text("You have been eliminated!", NamedTextColor.RED));
        } else {
            event.getKiller().setKills(event.getKiller().getKills() + 1);
            Utils.inGameBroadcast(killMessage);
        }
    }
}
