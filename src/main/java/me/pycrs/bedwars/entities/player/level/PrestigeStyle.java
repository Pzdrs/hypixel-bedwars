package me.pycrs.bedwars.entities.player.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.server.v1_16_R3.Tuple;

/**
 * Defines what a prestige looks like in chat
 */
public final class PrestigeStyle {
    /**
     * Defines what type of brackets are used
     */
    static final Tuple<String, String> BRACKETS = new Tuple<>("[", "]");

    /**
     * Representing the icon symbols in a prestige following the level number
     */
    enum Icon {
        CLASSIC(Component.text("\u272B")),
        PRIME(Component.text("\u272A")),
        ELEMENTAL(Component.text("\u269D", Style.style(TextDecoration.BOLD)));

        final Component component;

        Icon(Component component) {
            this.component = component;
        }
    }

    /**
     * Plain prestige, e.g. the Stone prestige
     */
    static final PrestigeStyle PLAIN = new PrestigeStyle();

    /**
     * Color for individual brackets
     */
    final TextColor openingBracket, closingBracket;
    /**
     * Solid color for the level
     */
    final TextColor level;
    /**
     * The icon following the level in chat
     */
    final Icon icon;
    /**
     * Color of the icon
     */
    final TextColor iconColor;
    /**
     * Each element in this array represents a color assigned to every digit
     */
    final TextColor[] digits;

    /**
     * Builder constructor
     */
    private PrestigeStyle(Builder builder) {
        this.openingBracket = builder.openingBracket;
        this.closingBracket = builder.closingBracket;
        this.level = builder.level;
        this.icon = builder.icon;
        this.iconColor = builder.iconColor;
        this.digits = builder.digits;
    }

    private PrestigeStyle() {
        this(new Builder());
    }

    /**
     * @return bare bones builder
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * @return predefined builder for the Prime prestige
     */
    static Builder primeBuilder() {
        return builder()
                .brackets(NamedTextColor.GRAY)
                .icon(Icon.PRIME);
    }

    /**
     * The base Elemental prestige builder
     *
     * @param openingBracketFirst color of the opening bracket and the first digit
     * @param secondThird color of the second and third digit
     * @param fourth color of the fourth digit
     * @param iconColor color of the icon
     * @param closingBracket color of the closing bracket
     * @return predefined builder for the Elemental prestige
     */
    static Builder elementalBuilder(TextColor openingBracketFirst, TextColor secondThird, TextColor fourth, TextColor iconColor, TextColor closingBracket) {
        return builder()
                .openingBracket(openingBracketFirst)
                .digits(openingBracketFirst, secondThird, secondThird, fourth)
                .iconColor(iconColor)
                .closingBracket(closingBracket)
                .icon(Icon.ELEMENTAL);
    }

    /**
     * @param openingBracketFirst color of the opening bracket and the first digit
     * @param secondThird color of the second and third digit
     * @param rest color of the fourth digit and the icon
     * @param closingBracket color of the closing bracket
     * @return predefined builder for the Elemental prestige
     */
    static Builder elementalBuilder(TextColor openingBracketFirst, TextColor secondThird, TextColor rest, TextColor closingBracket) {
        return elementalBuilder(openingBracketFirst, secondThird, rest, rest, closingBracket);
    }

    /**
     * @param openingBracketFirst color of the opening bracket and the first digit
     * @param secondThird color of the second and third digit
     * @param rest color of the fourth digit, the icon and the closing bracket
     * @return predefined builder for the Elemental prestige
     */
    static Builder elementalBuilder(TextColor openingBracketFirst, TextColor secondThird, TextColor rest) {
        return elementalBuilder(openingBracketFirst, secondThird, rest, rest, rest);
    }


    /**
     * {@code PrestigeStyle} builder static inner class.
     */
    static final class Builder {
        private TextColor openingBracket, closingBracket;
        private TextColor level;
        private Icon icon;
        private TextColor iconColor;
        private TextColor[] digits;

        /**
         * Default values
         */
        public Builder() {
            this.openingBracket = NamedTextColor.GRAY;
            this.closingBracket = NamedTextColor.GRAY;
            this.level = NamedTextColor.GRAY;
            this.icon = Icon.CLASSIC;
            this.iconColor = NamedTextColor.GRAY;
        }

        /**
         * Sets both {@code openingBracket} and {@code closingBracket} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param color the {@code openingBracket} and {@code closingBracket} to set
         * @return a reference to this Builder
         */
        public Builder brackets(TextColor color) {
            openingBracket = color;
            closingBracket = color;
            return this;
        }

        /**
         * Sets the {@code digits} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param digits the {@code digits} to set
         * @return a reference to this Builder
         */
        public Builder digits(TextColor... digits) {
            this.digits = digits;
            return this;
        }

        /**
         * Sets all the components and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param color the color to set for all components of a prestige
         * @return a reference to this Builder
         */
        public Builder solid(TextColor color) {
            openingBracket = color;
            closingBracket = color;
            level = color;
            iconColor = color;
            return this;
        }

        /**
         * Sets the {@code openingBracket} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param openingBracket the {@code openingBracket} to set
         * @return a reference to this Builder
         */
        public Builder openingBracket(TextColor openingBracket) {
            this.openingBracket = openingBracket;
            return this;
        }

        /**
         * Sets the {@code closingBracket} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param closingBracket the {@code closingBracket} to set
         * @return a reference to this Builder
         */
        public Builder closingBracket(TextColor closingBracket) {
            this.closingBracket = closingBracket;
            return this;
        }

        /**
         * Sets the {@code level} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param level the {@code level} to set
         * @return a reference to this Builder
         */
        public Builder level(TextColor level) {
            this.level = level;
            return this;
        }

        /**
         * Sets the {@code iconColor} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param color the {@code color} to set
         * @return a reference to this Builder
         */
        public Builder iconColor(TextColor color) {
            this.iconColor = color;
            return this;
        }

        /**
         * Sets the {@code icon} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param icon the {@code icon} to set
         * @return a reference to this Builder
         */
        public Builder icon(Icon icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Returns a {@code PrestigeStyle} built from the parameters previously set.
         *
         * @return a {@code PrestigeStyle} built with parameters of this {@code PrestigeStyle.Builder}
         */
        public PrestigeStyle build() {
            return new PrestigeStyle(this);
        }
    }
}
