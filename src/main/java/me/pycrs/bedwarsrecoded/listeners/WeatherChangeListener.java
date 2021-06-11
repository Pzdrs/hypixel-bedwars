package me.pycrs.bedwarsrecoded.listeners;

import me.pycrs.bedwarsrecoded.BedWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {
    private BedWars plugin;

    public WeatherChangeListener(BedWars plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
       if (event.getCause() == WeatherChangeEvent.Cause.NATURAL) event.setCancelled(true);
    }
}
