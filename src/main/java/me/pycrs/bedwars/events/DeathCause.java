package me.pycrs.bedwars.events;

import net.kyori.adventure.text.Component;

import java.util.Random;

public enum DeathCause {
    OTHER(new Component[]{}), VOID(new Component[]{}), PLAYER_ATTACK(new Component[]{});

    private Component[] messages;

    DeathCause(Component[] messages) {
        this.messages = messages;
    }

    public Component getRandomMessage() {
        return messages[new Random().nextInt(messages.length)];
    }
}
