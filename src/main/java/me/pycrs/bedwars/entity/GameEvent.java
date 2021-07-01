package me.pycrs.bedwars.entity;

import me.pycrs.bedwars.util.Utils;
import me.pycrs.bedwars.entity.dependency.GameEventHandler;
import net.kyori.adventure.text.Component;

public class GameEvent {
    private GameEvent before, after;
    private GameEventHandler handler;
    private Component broadcast;
    private int period;

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
            if (handler != null) handler.handle();
        }

        if (after != null) after.proceed(currentTime);
    }

    public static final class Builder {
        private GameEvent prior;
        private GameEvent after;
        private GameEventHandler handler;
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

        public Builder handle(GameEventHandler gameEventHandler) {
            handler = gameEventHandler;
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
