package me.pycrs.bedwars.events;

import me.pycrs.bedwars.Bedwars;
import me.pycrs.bedwars.entities.player.BedwarsPlayer;
import org.bukkit.entity.Player;

public abstract class BedwarsPlayerEvent extends BedwarsEvent {
    protected final BedwarsPlayer bedwarsPlayer;

    public BedwarsPlayerEvent(Bedwars plugin, BedwarsPlayer bedwarsPlayer) {
        super(plugin);
        this.bedwarsPlayer = bedwarsPlayer;
    }

    public BedwarsPlayer getBedwarsPlayer() {
        return bedwarsPlayer;
    }

    public Player getPlayer() {
        return bedwarsPlayer.getPlayer();
    }
}
