package me.pycrs.bedwars;

public enum Mode {
    SOLO(1, 8, 2, "Solo"),
    DOUBLES(2, 8,4, "Doubles"),
    TRIOS(3, 4, 6, "3v3v3v3"),
    SQUADS(4, 4, 8, "4v4v4v4");

    private final int teamSize, amountOfTeams, minPlayers;
    private final String displayName;

    Mode(int teamSize, int amountOfTeams, int minPlayers, String displayName) {
        this.teamSize = teamSize;
        this.amountOfTeams = amountOfTeams;
        this.minPlayers = minPlayers;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public int getAmountOfTeams() {
        return amountOfTeams;
    }
}
