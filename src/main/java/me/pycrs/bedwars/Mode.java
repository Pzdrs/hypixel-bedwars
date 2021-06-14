package me.pycrs.bedwars;

public enum Mode {
    SOLO(1, 8, 2),
    DOUBLES(2, 8,4),
    TRIOS(3, 4, 6),
    SQUADS(4, 4, 8);

    private final int teamSize, amountOfTeams, minPlayers;

    Mode(int teamSize, int amountOfTeams, int minPlayers) {
        this.teamSize = teamSize;
        this.amountOfTeams = amountOfTeams;
        this.minPlayers = minPlayers;
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
