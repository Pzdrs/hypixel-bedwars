package me.pycrs.bedwars.scoreboard;

import me.pycrs.bedwars.scoreboard.body.ScoreboardBody;
import me.pycrs.bedwars.scoreboard.body.line.ScoreboardLine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public abstract class BedwarsScoreboard {
    protected final Scoreboard scoreboard;
    private final Objective objective;
    private final ScoreboardBody body;

    public BedwarsScoreboard(String id, Component title, ScoreboardBody body) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(id, "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.body = body;
        this.body.setScoreboard(scoreboard, objective);
        render();
    }

    public void registerTeam(String name, String[] entries) {
        Team team = scoreboard.registerNewTeam(name);
        for (String entry : entries) team.addEntry(entry);
    }

    public ScoreboardBody getBody() {
        return body;
    }

    private void render() {
        for (int i = 0; i < body.getLines().size(); i++) {
            ScoreboardLine line = body.getLines().get(i);
            if (line == null) continue;
            Score score = objective.getScore(LegacyComponentSerializer.legacySection().serialize(line.toComponent()));
            int order = body.getLines().size() - i;
            line.setScore(order);
            score.setScore(order);
        }
    }
}
