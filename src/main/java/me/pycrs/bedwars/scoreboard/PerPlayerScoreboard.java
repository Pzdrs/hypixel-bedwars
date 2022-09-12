package me.pycrs.bedwars.scoreboard;

import me.pycrs.bedwars.scoreboard.body.ScoreboardBody;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class PerPlayerScoreboard extends BedwarsScoreboard {
    private final Player player;

    public PerPlayerScoreboard(String id, Player player, Component title, ScoreboardBody body) {
        super(id, title, body);
        this.player = player;
    }

    public void show() {
        player.setScoreboard(scoreboard);
    }
}
