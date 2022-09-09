package me.pycrs.bedwars.entities.player.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;


public enum BedwarsPrestige {
    /*===== BASIC PRESTIGE =====*/

    STONE(PrestigeStyle.PLAIN),
    IRON(PrestigeStyle.builder()
            .solid(NamedTextColor.WHITE).build()),
    GOLD(PrestigeStyle.builder()
            .solid(NamedTextColor.GOLD).build()),
    DIAMOND(PrestigeStyle.builder()
            .solid(NamedTextColor.AQUA).build()),
    EMERALD(PrestigeStyle.builder()
            .solid(NamedTextColor.DARK_GREEN).build()),
    SAPPHIRE(PrestigeStyle.builder()
            .solid(NamedTextColor.DARK_AQUA).build()),
    RUBY(PrestigeStyle.builder()
            .solid(NamedTextColor.DARK_RED).build()),
    CRYSTAL(PrestigeStyle.builder()
            .solid(NamedTextColor.LIGHT_PURPLE).build()),
    OPAL(PrestigeStyle.builder()
            .solid(NamedTextColor.BLUE).build()),
    AMETHYST(PrestigeStyle.builder()
            .solid(NamedTextColor.DARK_PURPLE).build()),
    RAINBOW(PrestigeStyle.builder()
            .openingBracket(NamedTextColor.RED)
            .digits(NamedTextColor.GOLD, NamedTextColor.YELLOW, NamedTextColor.GREEN, NamedTextColor.AQUA)
            .iconColor(NamedTextColor.LIGHT_PURPLE)
            .closingBracket(NamedTextColor.DARK_PURPLE).build()),

    /*===== PRIME PRESTIGE =====*/

    IRON_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.WHITE)
            .iconColor(NamedTextColor.GRAY).build()),
    GOLD_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.YELLOW)
            .iconColor(NamedTextColor.GOLD).build()),
    DIAMOND_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.AQUA)
            .iconColor(NamedTextColor.DARK_AQUA).build()),
    EMERALD_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.GREEN)
            .iconColor(NamedTextColor.DARK_GREEN).build()),
    SAPPHIRE_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.DARK_AQUA)
            .iconColor(NamedTextColor.BLUE).build()),
    RUBY_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.RED)
            .iconColor(NamedTextColor.DARK_RED).build()),
    CRYSTAL_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.LIGHT_PURPLE)
            .iconColor(NamedTextColor.DARK_PURPLE).build()),
    OPAL_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.BLUE)
            .iconColor(NamedTextColor.DARK_BLUE).build()),
    AMETHYST_PRIME(PrestigeStyle.primeBuilder()
            .level(NamedTextColor.DARK_PURPLE)
            .iconColor(NamedTextColor.DARK_GRAY).build()),
    MIRROR(PrestigeStyle.primeBuilder()
            .brackets(NamedTextColor.DARK_GRAY)
            .digits(NamedTextColor.GRAY, NamedTextColor.WHITE, NamedTextColor.WHITE, NamedTextColor.GRAY)
            .iconColor(NamedTextColor.GRAY).build()),

    /*===== ELEMENTAL PRESTIGE =====*/

    LIGHT(PrestigeStyle.elementalBuilder(
            NamedTextColor.WHITE,
            NamedTextColor.YELLOW,
            NamedTextColor.GOLD).build()),
    DAWN(PrestigeStyle.elementalBuilder(
            NamedTextColor.GOLD,
            NamedTextColor.WHITE,
            NamedTextColor.AQUA,
            NamedTextColor.DARK_AQUA).build()),
    // The only elemental prestige where the last digit isn't the same color as the icon
    DUSK(PrestigeStyle.elementalBuilder(
            NamedTextColor.DARK_PURPLE,
            NamedTextColor.LIGHT_PURPLE,
            NamedTextColor.GOLD,
            NamedTextColor.YELLOW,
            NamedTextColor.YELLOW).build()),
    AIR(PrestigeStyle.elementalBuilder(
            NamedTextColor.AQUA,
            NamedTextColor.WHITE,
            NamedTextColor.GRAY,
            NamedTextColor.DARK_GRAY).build()),
    WIND(PrestigeStyle.elementalBuilder(
            NamedTextColor.WHITE,
            NamedTextColor.GREEN,
            NamedTextColor.DARK_GREEN).build()),
    NEBULA(PrestigeStyle.elementalBuilder(
            NamedTextColor.DARK_RED,
            NamedTextColor.RED,
            NamedTextColor.LIGHT_PURPLE,
            NamedTextColor.DARK_PURPLE).build()),
    THUNDER(PrestigeStyle.elementalBuilder(
            NamedTextColor.YELLOW,
            NamedTextColor.WHITE,
            NamedTextColor.DARK_GRAY).build()),
    EARTH(PrestigeStyle.elementalBuilder(
            NamedTextColor.GREEN,
            NamedTextColor.DARK_GREEN,
            NamedTextColor.GOLD,
            NamedTextColor.YELLOW).build()),
    WATER(PrestigeStyle.elementalBuilder(
            NamedTextColor.AQUA,
            NamedTextColor.DARK_AQUA,
            NamedTextColor.BLUE,
            NamedTextColor.DARK_BLUE).build()),
    FIRE(PrestigeStyle.elementalBuilder(
            NamedTextColor.YELLOW,
            NamedTextColor.GOLD,
            NamedTextColor.RED,
            NamedTextColor.DARK_RED).build());

    private final PrestigeStyle style;

    BedwarsPrestige(PrestigeStyle style) {
        this.style = style;
    }

    public static final BedwarsPrestige HIGHEST_PRESTIGE = values()[values().length - 1];

    public static BedwarsPrestige of(int order) {
        return values()[order];
    }

    Component color(int level) {
        Component openingBracket = Component.text(PrestigeStyle.BRACKETS.a(), style.openingBracket);
        Component stars = Component.empty();
        if (style.digits != null) {
            int[] digits = String.valueOf(level).chars().map(Character::getNumericValue).toArray();
            for (int i = 0; i < digits.length; i++) {
                int digit = digits[i];
                if (style.digits.length >= i) {
                    stars = stars.append(Component.text(digit, style.digits[i]));
                } else {
                    stars = stars.append(Component.text(digit, NamedTextColor.GRAY));
                }
            }
        } else stars = Component.text(level, style.level);
        Component icon = style.icon.component.color(style.iconColor);
        Component closingBracket = Component.text(PrestigeStyle.BRACKETS.b(), style.closingBracket);
        return Component.empty()
                .append(openingBracket)
                .append(stars)
                .append(icon)
                .append(closingBracket);
    }
}
