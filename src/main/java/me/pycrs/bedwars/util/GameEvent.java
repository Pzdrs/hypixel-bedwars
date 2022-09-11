package me.pycrs.bedwars.util;

import net.kyori.adventure.text.Component;

public class GameEvent {
    private GameEvent before, after;
    private Runnable handler;
    private Component broadcast;
    private int period;

    private GameEvent() {
    }

    private GameEvent(Builder builder) {
        before = builder.prior;
        after = builder.after;
        handler = builder.handler;
        broadcast = builder.broadcast;
        period = builder.period;
    }

    public void proceed(int currentTime) {
        if (before != null) before.proceed(currentTime);

        if (currentTime == period) {
            if (broadcast != null) Utils.inGameBroadcast(broadcast);
            if (handler != null) handler.run();
        }

        if (after != null) after.proceed(currentTime);
    }

    public static final class Builder {
        private GameEvent prior;
        private GameEvent after;
        private Runnable handler;
        private Component broadcast;
        private int period;

        public Builder() {
        }

        public Builder broadcast(Component component) {
            broadcast = component;
            return this;
        }

        public Builder before(GameEvent gameEvent) {
            prior = gameEvent;
            return this;
        }

        public Builder after(GameEvent gameEvent) {
            after = gameEvent;
            return this;
        }

        public Builder handle(Runnable handler) {
            this.handler = handler;
            return this;
        }

        public Builder period(int period) {
            this.period = period;
            return this;
        }

        public GameEvent build() {
            return new GameEvent(this);
        }
    }
}
