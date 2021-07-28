package me.pycrs.bedwars.entities.team;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;

public enum TeamColor {
    RED(Color.RED, NamedTextColor.RED),
    BLUE(Color.BLUE, NamedTextColor.BLUE),
    GREEN(Color.GREEN, NamedTextColor.GREEN),
    YELLOW(Color.YELLOW, NamedTextColor.YELLOW),
    AQUA(Color.AQUA, NamedTextColor.AQUA),
    WHITE(Color.WHITE, NamedTextColor.WHITE),
    PINK(Color.PURPLE, NamedTextColor.LIGHT_PURPLE),
    GRAY(Color.GRAY, NamedTextColor.GRAY);

    private Color color;
    private NamedTextColor textColor;

    TeamColor(Color color, NamedTextColor textColor) {
        this.color = color;
        this.textColor = textColor;
    }

    public NamedTextColor getTextColor() {
        return textColor;
    }

    public Color getColor() {
        return color;
    }

    public Component getFriendlyName() {
        return Component.text(WordUtils.capitalize(toString().toLowerCase()), getTextColor());
    }

    public Component getDisplay() {
        return getFriendlyName()
                .append(Component.text(" Team"));
    }

    public Component getBedDisplay() {
        return getFriendlyName()
                .append(Component.text(" Bed"));
    }
}
