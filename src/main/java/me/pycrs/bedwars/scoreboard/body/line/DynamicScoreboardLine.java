package me.pycrs.bedwars.scoreboard.body.line;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.function.Supplier;

public class DynamicScoreboardLine extends SimpleScoreboardLine {
    private final String id;
    private final Component separator, staticContent;
    private final Supplier<Component> dynamicContent;

    /**
     * Used for partly dynamic lines, i.e. a part of a line stays the same while the other changes (e.g. player count, counters, ...)
     *
     * @param id                     used for accessing the line, if there are more than one dynamic lines with the same {@code id}, all of them
     *                               get updated upon the call to {@link DynamicScoreboardLine#update(Scoreboard, Objective)}
     * @param staticContent          part of the line that doesn't change
     * @param separator              static/dynamic separator, e.g. dash, space, etc..
     * @param dynamicContentSupplier {@link Supplier<Component>} which gets called every time a line is updated returning
     *                               the relevant data at that point in time
     */
    public DynamicScoreboardLine(String id, Component staticContent, Component separator, Supplier<Component> dynamicContentSupplier) {
        super(Component.empty()
                .append(staticContent)
                .append(separator)
                .append(dynamicContentSupplier.get())
        );
        this.id = id;
        this.staticContent = staticContent;
        this.separator = separator;
        this.dynamicContent = dynamicContentSupplier;
    }

    /**
     * Used for fully dynamic lines, i.e. the whole lines changes
     *
     * @param id             used for accessing the line, if there are more than one dynamic lines with the same {@code id}, all of them
     *                       get updated upon the call to {@link DynamicScoreboardLine#update(Scoreboard, Objective)}
     * @param dynamicContent {@link Supplier<Component>} which gets called every time a line is updated returning
     *                       the relevant data at that point in time
     */
    public DynamicScoreboardLine(String id, Supplier<Component> dynamicContent) {
        this(id, Component.empty(), Component.empty(), dynamicContent);
    }

    public void update(Scoreboard scoreboard, Objective objective) {
        scoreboard.resetScores(LegacyComponentSerializer.legacySection().serialize(content));
        content = Component.empty()
                .append(staticContent)
                .append(separator)
                .append(dynamicContent.get());
        Score updatedScore = objective.getScore(LegacyComponentSerializer.legacySection().serialize(content));
        updatedScore.setScore(score);
    }

    protected void modify(Scoreboard scoreboard, Component content) {

    }

    public String getId() {
        return id;
    }
}
