package me.pycrs.bedwars.entities.player.level;

import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_16_R3.Tuple;
import org.bukkit.entity.Player;

public final class HypixelBedwarsLevel {
    public final static HypixelBedwarsLevel DEFAULT = new HypixelBedwarsLevel(0);
    private final double experience;
    private final int level;
    private final BedwarsPrestige prestige;

    public HypixelBedwarsLevel(double experience) {
        this.experience = experience;
        this.level = HypixelExperienceCalculator.getLevelForExp(experience);
        this.prestige = HypixelExperienceCalculator.getPrestigeForExp(experience);
    }

    public int getLevel() {
        return level;
    }

    public Component toComponent() {
        return prestige.color(level);
    }

    public void show(Player player) {
        Tuple<Double, Double> progress = HypixelExperienceCalculator.getLevelProgress(experience);
        // Display the progress towards the next level
        player.setExp((float) (progress.a() / progress.b()));
    }
}
