package me.pycrs.bedwarsrecoded;

import net.kyori.adventure.text.format.NamedTextColor;

public enum TeamColor {
    RED(NamedTextColor.RED),
    BLUE(NamedTextColor.BLUE),
    GREEN(NamedTextColor.GREEN),
    YELLOW(NamedTextColor.YELLOW),
    AQUA(NamedTextColor.AQUA),
    WHITE(NamedTextColor.WHITE),
    PINK(NamedTextColor.LIGHT_PURPLE),
    GRAY(NamedTextColor.GRAY);

    private NamedTextColor color;

    TeamColor(NamedTextColor color) {
        this.color = color;
    }

    public NamedTextColor getColor() {
        return color;
    }
}
