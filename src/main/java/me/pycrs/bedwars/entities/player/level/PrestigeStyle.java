package me.pycrs.bedwars.entities.player.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.server.v1_16_R3.Tuple;

public final class PrestigeStyle {
    static final Tuple<String, String> BRACKETS = new Tuple<>("[", "]");

    enum Icon {
        CLASSIC(Component.text("\u272B")),
        PRIME(Component.text("\u272A")),
        ELEMENTAL(Component.text("\u269D", Style.style(TextDecoration.BOLD)));

        final Component component;

        Icon(Component component) {
            this.component = component;
        }
    }

    static final PrestigeStyle PLAIN = new PrestigeStyle();

    private interface CustomStyle {
        Component apply(TextColor openingBracket, TextColor value, TextColor icon, TextColor closingBracket);
    }

    final TextColor openingBracket, closingBracket;
    final TextColor level, iconColor;
    final Icon icon;
    final TextColor[] digits;


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

    static Builder builder() {
        return new Builder();
    }

    static Builder primeBuilder() {
        return builder()
                .brackets(NamedTextColor.GRAY)
                .icon(Icon.PRIME);
    }

    static Builder elementalBuilder(TextColor openingBracketFirst, TextColor secondThird, TextColor fourth, TextColor iconColor, TextColor closingBracket) {
        return builder()
                .openingBracket(openingBracketFirst)
                .digits(openingBracketFirst, secondThird, secondThird, fourth)
                .iconColor(iconColor)
                .closingBracket(closingBracket)
                .icon(Icon.ELEMENTAL);
    }

    static Builder elementalBuilder(TextColor openingBracketFirst, TextColor secondThird, TextColor rest, TextColor closingBracket) {
        return elementalBuilder(openingBracketFirst, secondThird, rest, rest, closingBracket);
    }

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
         * Defaults, i.e. Stone prestige
         */
        public Builder() {
            this.openingBracket = NamedTextColor.GRAY;
            this.closingBracket = NamedTextColor.GRAY;
            this.level = NamedTextColor.GRAY;
            this.icon = Icon.CLASSIC;
            this.iconColor = NamedTextColor.GRAY;
        }

        public Builder brackets(TextColor val) {
            openingBracket = val;
            closingBracket = val;
            return this;
        }

        public Builder custom(CustomStyle consumer) {
            return this;
        }

        public Builder digits(TextColor... digits) {
            this.digits = digits;
            return this;
        }

        public Builder solid(TextColor val) {
            openingBracket = val;
            closingBracket = val;
            level = val;
            iconColor = val;
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
         * Sets the {@code icon} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param color the {@code color} to set
         * @return a reference to this Builder
         */
        public Builder iconColor(TextColor color) {
            this.iconColor = color;
            return this;
        }

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
