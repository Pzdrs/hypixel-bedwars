package me.pycrs.bedwars;

import me.pycrs.bedwars.util.Utils;

import java.util.Optional;

public enum Mode {
    SOLO(1, 8, "Solo"),
    DOUBLES(2, 8, "Doubles"),
    TRIOS(3, 4, "3v3v3v3"),
    SQUADS(4, 4, "4v4v4v4");

    private final int teamSize, amountOfTeams;
    private final String displayName;

    Mode(int teamSize, int amountOfTeams, String displayName) {
        this.teamSize = teamSize;
        this.amountOfTeams = amountOfTeams;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * @return the minimal possible amount of players for a game of two full teams to start
     */
    public int getMinPlayers() {
        return 2 * teamSize;
    }

    /**
     * @return the required amount of players for the lobby countdown to begin
     */
    public int getRequiredPlayers() {
        return teamSize * amountOfTeams - 1;
    }

    // TODO: 9/12/2022 for development purposes, change for production

    /**
     * @param players the current amount of online players
     * @return true if there is enough players for the game to start counting down, false otherwise
     */
    public boolean isEnough(int players) {
        return /*players >= getRequiredPlayers()*/players >= 2;
    }

    /**
     * @param players the current amount of online players
     * @return true if we have a full lobby, false otherwise
     */
    public boolean isFull(int players) {
        return players >= teamSize * amountOfTeams;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public int getAmountOfTeams() {
        return amountOfTeams;
    }

    public boolean isSoloOrDoubles() {
        return Utils.atLeastOneEquals(this, new Mode[]{SOLO, DOUBLES});
    }

    public static Optional<Mode> of(int teamSize) {
        for (Mode mode : Mode.values()) {
            if (mode.getTeamSize() == teamSize) return Optional.of(mode);
        }
        Bedwars.getInstance().getLogger().severe("A team can't have " + teamSize + " players. Supported team sizes: 1, 2, 3 or 4");
        return Optional.empty();
    }
}
