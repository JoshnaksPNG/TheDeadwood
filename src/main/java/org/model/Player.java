package org.model;

import jdk.jshell.spi.ExecutionControl;

public class Player
{
    int playerNumber;

    int rank;

    int money;

    int credit;

    Role role;

    boolean isInRole;

    int practiceChips;

    int currPositionX;
    int currPositionY;

    Player(int playerNumber, int rank, int money, int credit, int playerX, int playerY)
    {
        this.playerNumber = playerNumber;
        this.rank = rank;
        this.money = money;
        this.credit = credit;
        this.currPositionX = playerX;
        this.currPositionY = playerY;
    }

    void takeTurn() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    boolean takeRole(Scene scene, Role role) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    boolean move(int targetX, int targetY) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
