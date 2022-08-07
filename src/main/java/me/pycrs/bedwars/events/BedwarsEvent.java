package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import org.bukkit.event.Event;

public abstract class BedwarsEvent extends Event {
    protected final Bedwars plugin;

    public BedwarsEvent(Bedwars plugin) {
        this.plugin = plugin;
    }

    public Bedwars getPlugin() {
        return plugin;
    }
}
