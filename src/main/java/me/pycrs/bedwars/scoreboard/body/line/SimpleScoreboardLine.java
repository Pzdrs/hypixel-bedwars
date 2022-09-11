package me.pycrs.bedwars.scoreboard.body.line;

import net.kyori.adventure.text.Component;

public class SimpleScoreboardLine implements ScoreboardLine{
    protected Component content;
    protected int score;

    public SimpleScoreboardLine(Component content) {
        this.content = content;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public Component toComponent() {
        return content;
    }

    @Override
    public int getScore() {
        return score;
    }
}
