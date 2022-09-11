package me.pycrs.bedwars.entities.team;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Color;
import org.bukkit.entity.Player;

public enum TeamColor {
    RED(Color.RED, NamedTextColor.RED),
    BLUE(Color.BLUE, NamedTextColor.BLUE),
    GREEN(Color.GREEN, NamedTextColor.GREEN),
    YELLOW(Color.YELLOW, NamedTextColor.YELLOW),
    AQUA(Color.AQUA, NamedTextColor.AQUA),
    WHITE(Color.WHITE, NamedTextColor.WHITE),
    PINK(Color.PURPLE, NamedTextColor.LIGHT_PURPLE),
    GRAY(Color.GRAY, NamedTextColor.DARK_GRAY);

    private final Color color;
    private final NamedTextColor textColor;

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
        return getFriendlyNamePlain().color(textColor);
    }

    public Component getFriendlyNamePlain() {
        return Component.text(WordUtils.capitalize(name().toLowerCase()));
    }

    public Component getTeamLetter() {
        return Component.text(name().charAt(0), textColor);
    }

    public Component getPlayerListName(Player player) {
        return Component.text()
                .append(getTeamLetter().decorate(TextDecoration.BOLD))
                .append(Component.space())
                .append(player.displayName().color(textColor))
                .build();
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
