package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
        // Set player's display name
        event.getPlayer().displayName(Component.text(event.getPlayer().getName()).color(TextColor.fromHexString("#AAAAAA")));


    }
}
