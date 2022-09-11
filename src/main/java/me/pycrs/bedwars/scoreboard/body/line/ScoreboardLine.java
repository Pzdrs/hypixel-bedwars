package me.pycrs.bedwars.scoreboard.body.line;

import net.kyori.adventure.text.Component;

public interface ScoreboardLine {
    Component toComponent();
    int getScore();
    void setScore(int score);
}
