package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import me.pycrs.bedwarsrecoded.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private BedWars bedWars;

    public PlayerJoinListener(BedWars bedWars) {
        this.bedWars = bedWars;
        bedWars.getServer().getPluginManager().registerEvents(this, bedWars);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Get rid of the default join message
        event.joinMessage(null);

        Player player = event.getPlayer();

        // Set player's display name
        player.displayName(Component.text(event.getPlayer().getName(), NamedTextColor.GRAY));

        // New join messages
        bedWars.getServer().sendMessage(player.displayName()
                .append(Component.text(" has joined ", NamedTextColor.YELLOW))
                .append(Component.text(
                        Utils.color("&e(&b" + bedWars.getServer().getOnlinePlayers().size() + "&e/&b" +
                                bedWars.getMode().getTeamSize() * bedWars.getMode().getAmountOfTeams() + "&e)!"))));

        // If enough players, start the countdown
        if (bedWars.getServer().getOnlinePlayers().size() >= bedWars.getMode().getMinPlayers()) {
            // TODO: 4/6/2021 Countdown
        }
    }
}
