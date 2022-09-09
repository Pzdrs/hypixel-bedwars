package me.pycrs.bedwars.entities.player.level;

import net.minecraft.server.v1_16_R3.Tuple;
import org.javatuples.Triplet;

import java.util.Arrays;

public final class HypixelExperienceCalculator {
    private HypixelExperienceCalculator() {
        throw new AssertionError();
    }

    private static final double XP_PER_LEVEL = 5000;

    private static final int LEVELS_PER_PRESTIGE = 100;
    private static final int EASY_LEVELS = 4;
    private static final int[] EASY_LEVELS_XP = new int[]{500, 1000, 2000, 3500};

    private static final double EASY_LEVELS_XP_TOTAL = Arrays.stream(EASY_LEVELS_XP).sum();

    private static final double XP_PER_PRESTIGE = (LEVELS_PER_PRESTIGE - EASY_LEVELS_XP.length) * XP_PER_LEVEL + EASY_LEVELS_XP_TOTAL;

    /**
     * @param experience experience
     * @return an instance of {@link BedwarsPrestige} corresponding to the passed in experience
     */
    static BedwarsPrestige getPrestigeForExp(double experience) {
        return getPrestigeForLevel(getLevelForExp(experience));
    }

    /**
     * @param level level
     * @return an instance of {@link BedwarsPrestige} corresponding to the passed in level
     */
    static BedwarsPrestige getPrestigeForLevel(int level) {
        int prestige = level / LEVELS_PER_PRESTIGE;
        return BedwarsPrestige.of(Math.min(prestige, BedwarsPrestige.HIGHEST_PRESTIGE.ordinal()));
    }

    /**
     * Extracts all valuable data from the experience value
     *
     * @param experience experience
     * @return an instance of {@code Triplet<Integer, Double,Double>}, first value is the calculated level, second
     * is the progress towards the next level, third value is the experience requirement for the next level
     */
    static Triplet<Integer, Double, Double> extractLevelData(double experience) {
        int prestige = (int) (experience / XP_PER_PRESTIGE);
        int level = prestige * LEVELS_PER_PRESTIGE;

        double expWithoutPrestiges = experience - (prestige * XP_PER_PRESTIGE);
        for (int i = 1; i <= EASY_LEVELS; i++) {
            double expForEasyLevel = getExpForLevel(i);
            if (expWithoutPrestiges < expForEasyLevel) break;
            level++;
            expWithoutPrestiges -= expForEasyLevel;
        }
        level += expWithoutPrestiges / XP_PER_LEVEL;
        return new Triplet<>(level, expWithoutPrestiges % XP_PER_LEVEL, getExpForLevel(level + 1));
    }

    /**
     * @param experience experience
     * @return an instance of {@code Tuple<Double, Double>} with two values, first value corresponds to current
     * progress towards the next level, the second value is the required amount of experience to hit the next level
     */
    static Tuple<Double, Double> getLevelProgress(double experience) {
        Triplet<Integer, Double, Double> level = extractLevelData(experience);
        return new Tuple<>(level.getValue1(), level.getValue2());
    }

    /**
     * @param experience experience
     * @return the actual level (stars) the player has
     */
    static int getLevelForExp(double experience) {
        return extractLevelData(experience).getValue0();
    }

    /**
     * @param level level (stars)
     * @return the required amount of experience towards the next level, i.e. for level 10 => 5000, level 101 => 500
     */
    static double getExpForLevel(int level) {
        if (level == 0) return 0;
        int respectedLevel = getPrestigeLevel(level);
        if (respectedLevel <= EASY_LEVELS) return EASY_LEVELS_XP[respectedLevel - 1];
        return XP_PER_LEVEL;
    }

    /**
     * @return "2" instead of "102" if prestiges happen every 100 levels e.g.
     */
    static int getPrestigeLevel(int level) {
        if (level > BedwarsPrestige.HIGHEST_PRESTIGE.ordinal() * LEVELS_PER_PRESTIGE) {
            return level - BedwarsPrestige.HIGHEST_PRESTIGE.ordinal() * LEVELS_PER_PRESTIGE;
        } else {
            return level % LEVELS_PER_PRESTIGE;
        }
    }
}
