package me.pycrs.bedwars.events;

import net.kyori.adventure.text.Component;

public interface BedwarsEventWithMessage {
    Component getMessage();

    void setMessage(Component component);
}
