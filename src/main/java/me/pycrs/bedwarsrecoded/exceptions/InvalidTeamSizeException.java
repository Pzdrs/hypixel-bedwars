package me.pycrs.bedwarsrecoded.exceptions;

public class InvalidTeamSizeException extends Exception {
    public InvalidTeamSizeException(int teamSize) {
        super("A team can't have " + teamSize + " players. Supported team sizes: 1, 2, 3 or 4");
    }
}
