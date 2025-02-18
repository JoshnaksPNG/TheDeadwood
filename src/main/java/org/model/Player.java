package org.model;

import jdk.jshell.spi.ExecutionControl;
import org.controller.BoardManager;

public class Player
{
    private int playerNumber;
    private int rank;
    private int money;
    private int credit;
    private Role role;
    private boolean isInRole;
    private int practiceChips;
    private int currPositionX;
    private int currPositionY;

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



    // Getter and Setter for playerNumber
    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    // Getter and Setter for rank
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    // Getter and Setter for money
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // Getter and Setter for credit
    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    // Getter and Setter for role
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Getter and Setter for isInRole
    public boolean isInRole() {
        return isInRole;
    }

    public void setInRole(boolean isInRole) {
        this.isInRole = isInRole;
    }

    // Getter and Setter for practiceChips
    public int getPracticeChips() {
        return practiceChips;
    }

    public void setPracticeChips(int practiceChips) {
        this.practiceChips = practiceChips;
    }

    // Getter and Setter for currPositionX
    public int getCurrPositionX() {
        return currPositionX;
    }

    public void setCurrPositionX(int currPositionX) {
        this.currPositionX = currPositionX;
    }

    // Getter and Setter for currPositionY
    public int getCurrPositionY() {
        return currPositionY;
    }

    public void setCurrPositionY(int currPositionY) {
        this.currPositionY = currPositionY;
    }
}
