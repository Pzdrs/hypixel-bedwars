package me.pycrs.bedwarsrecoded;

import org.bukkit.entity.Player;

public interface Trap {
    /**
     *
     * @param team Team, whose trap was triggered
     * @param player The player that triggered the trap
     */
    void trigger(BTeam team, Player player);
}
