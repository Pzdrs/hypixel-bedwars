package me.pycrs.bedwarsrecoded.traps;

import me.pycrs.bedwarsrecoded.BedwarsTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class AlarmTrap implements Trap {
    @Override
    public void trigger(BedwarsTeam team, Player player) {
        team.teamBroadcast(Component.text("Someones in ur base mate, watch out"));
        player.sendMessage(Component.text("You triggered a trap mate, nice job"));
    }
}
