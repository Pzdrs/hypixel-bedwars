package me.pycrs.bedwars.scoreboard.body;

import me.pycrs.bedwars.scoreboard.body.line.DynamicScoreboardLine;
import me.pycrs.bedwars.scoreboard.body.line.ScoreboardLine;
import me.pycrs.bedwars.scoreboard.body.line.SimpleScoreboardLine;
import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class ScoreboardBody {
    private Scoreboard scoreboard;
    private Objective objective;
    private final List<ScoreboardLine> lines;

    private ScoreboardBody(Builder builder) {
        lines = builder.lines;
    }

    public List<ScoreboardLine> getLines() {
        return new ArrayList<>(lines);
    }

    public void setScoreboard(Scoreboard scoreboard, Objective objective) {
        this.scoreboard = scoreboard;
        this.objective = objective;
    }

    public void updateLine(String id) {
        for (ScoreboardLine line : lines) {
            if (line instanceof DynamicScoreboardLine dynamicLine) {
                if (dynamicLine.getId().equals(id)) dynamicLine.update(scoreboard, objective);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@code Body} builder static inner class.
     */
    public static final class Builder {
        private final List<ScoreboardLine> lines;
        private final AtomicInteger emptyLines;

        public Builder() {
            this.lines = new ArrayList<>();
            this.emptyLines = new AtomicInteger(0);
        }

        /**
         * Sets the {@code lines} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param line the {@code lines} to set
         * @return a reference to this Builder
         */
        public Builder line(Supplier<SimpleScoreboardLine> line) {
            lines.add(line.get());
            return this;
        }

        public Builder lineAny(Supplier<ScoreboardLine> line) {
            lines.add(line.get());
            return this;
        }

        public Builder lines(Supplier<ScoreboardLine[]> lines) {
            this.lines.addAll(Arrays.asList(lines.get()));
            return this;
        }

        public Builder line(SimpleScoreboardLine line) {
            lines.add(line);
            return this;
        }

        public Builder dynamicLine(DynamicScoreboardLine line) {
            lines.add(line);
            return this;
        }

        public Builder dynamicLine(Supplier<DynamicScoreboardLine> line) {
            lines.add(line.get());
            return this;
        }


        public Builder newline() {
            lines.add(new SimpleScoreboardLine(Component.text(" ".repeat(emptyLines.getAndIncrement()))));
            return this;
        }

        /**
         * Returns a {@code Body} built from the parameters previously set.
         *
         * @return a {@code Body} built with parameters of this {@code Body.Builder}
         */
        public ScoreboardBody build() {
            return new ScoreboardBody(this);
        }
    }
}