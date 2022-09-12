package me.pycrs.bedwars;

/**
 * Represents what stage the game is currently in
 */
public enum GameStage {
    LOBBY_WAITING, LOBBY_COUNTDOWN, GAME_IN_PROGRESS, GAME_FINISHED;

    public boolean isGameInProgress() {
        return this == GAME_IN_PROGRESS;
    }

    public boolean isGameFinished() {
        return this == GAME_FINISHED;
    }

    public boolean isLobbyCountingDown() {
        return this == LOBBY_COUNTDOWN;
    }

    public boolean isLobby() {
        return this == LOBBY_WAITING || this == LOBBY_COUNTDOWN;
    }
}
