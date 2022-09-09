package me.pycrs.bedwars.entities.player.level;

import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_16_R3.Tuple;
import org.bukkit.entity.Player;

/**
 * Represents player's Hypixel bedwars level
 */
public final class HypixelBedwarsLevel {
    /**
     * Fallback instance
     */
    public final static HypixelBedwarsLevel DEFAULT = new HypixelBedwarsLevel(0);
    private final double experience;
    private final int level;
    private final BedwarsPrestige prestige;

    public HypixelBedwarsLevel(double experience) {
        this.experience = experience;
        this.level = HypixelExperienceCalculator.getLevelForExp(experience);
        this.prestige = HypixelExperienceCalculator.getPrestigeForExp(experience);
    }

    /**
     * @return chat representation of this level
     */
    public Component toComponent() {
        return prestige.color(level);
    }

    /**
     * Updates the player's xp bar
     *
     * @param player player
     */
    public void show(Player player) {
        Tuple<Double, Double> progress = HypixelExperienceCalculator.getLevelProgress(experience);
        // Reset the xp bar and apply new level
        player.setLevel(0);
        player.giveExpLevels(level);

        // Display the progress towards the next level
        player.setExp((float) (progress.a() / progress.b()));
    }
}
