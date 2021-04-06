package me.pycrs.bedwarsrecoded;

public enum Mode {
    SOLO(1, 8),
    DOUBLES(2, 8),
    TRIOS(3, 4),
    SQUADS(4, 4);

    private int teamSize, amountOfTeams;

    Mode(int teamSize, int amountOfTeams) {
        this.teamSize = teamSize;
        this.amountOfTeams = amountOfTeams;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public int getAmountOfTeams() {
        return amountOfTeams;
    }
}
