package me.pycrs.bedwars.scoreboard;

import me.pycrs.bedwars.scoreboard.body.ScoreboardBody;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GlobalBedwarsScoreboard extends BedwarsScoreboard {
    private final List<Player> players;

    public GlobalBedwarsScoreboard(String id, Component title, ScoreboardBody body) {
        super(id, title, body);
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setScoreboard(scoreboard);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
