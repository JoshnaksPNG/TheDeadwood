package org.model;

import jdk.jshell.spi.ExecutionControl;
import org.controller.BoardManager;

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

    public Player(int playerNumber, int rank, int money, int credit, int playerX, int playerY)
    {
        this.playerNumber = playerNumber;
        this.rank = rank;
        this.money = money;
        this.credit = credit;
        this.currPositionX = playerX;
        this.currPositionY = playerY;
    }

    public void takeTurn() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public boolean takeRole(Scene scene, Role role) throws ExecutionControl.NotImplementedException
    {
        if(role.roleRank > this.rank)
        {
            throw new RuntimeException("Player attempting to take role with higher rank!");
        }

        role.isOccupied = true;
        role.player = this;

        return true;
    }

    public boolean move(int targetX, int targetY, BoardManager board) throws ExecutionControl.NotImplementedException
    {
        boolean canMove = board.checkDestination(this, targetX, targetY);

        if(!canMove)
        {
            throw new RuntimeException("Player attempting to move to invalid destination");
        }

        currPositionY = targetY;
        currPositionX = targetX;

        board.movePlayer(this, targetX, targetY);

        return canMove;
    }
}
