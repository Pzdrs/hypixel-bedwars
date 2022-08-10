package me.pycrs.bedwars.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Convenience class for listeners, keeps reference to the main class and registers the listener
 *
 * @param <T> main class
 */
public abstract class BaseListener<T extends JavaPlugin> implements Listener {
    protected final T plugin;

    public BaseListener(T plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
