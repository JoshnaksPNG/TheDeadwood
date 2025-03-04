package org.model;

import java.util.ArrayList;

public class Role {
    private String name;
    private String line;
    private int rank;
    private boolean isMain;
    private boolean isOccupied;
    private Player occupant;

    public Role(String name, String line, int rank, boolean isMain) {
        this.name = name;
        this.line = line;
        this.rank = rank;
        this.isMain = isMain;
    }

    public String getName() {
        return name;
    }

    public int getRank() {
        return rank;
    }

    public String getLine() {
        return line;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setActor(Player player) {
        isOccupied = true;
        occupant = player;
    }

    public void printString() {
        System.out.println("Name = " + name + " Line = " + line + " Rank = " + rank + " isMain = " + isMain);
    }
}
