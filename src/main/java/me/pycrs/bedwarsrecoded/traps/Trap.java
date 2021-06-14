package me.pycrs.bedwarsrecoded.traps;

import me.pycrs.bedwarsrecoded.BedwarsTeam;
import org.bukkit.entity.Player;

public interface Trap {
    /**
     *
     * @param team Team, whose trap was triggered
     * @param player The player that triggered the trap
     */
    void trigger(BedwarsTeam team, Player player);
}
