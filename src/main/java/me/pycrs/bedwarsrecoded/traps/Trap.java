package me.pycrs.bedwarsrecoded.traps;

import me.pycrs.bedwarsrecoded.BTeam;
import org.bukkit.entity.Player;

public interface Trap {
    /**
     *
     * @param team Team, whose trap was triggered
     * @param player The player that triggered the trap
     */
    void trigger(BTeam team, Player player);
}