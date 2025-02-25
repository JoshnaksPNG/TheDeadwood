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

    private Room currentRoom;

    public Player(int playerNumber, int rank, int money, int credit, int playerX, int playerY)
    {
        this.playerNumber = playerNumber;
        this.rank = rank;
        this.money = money;
        this.credit = credit;
        this.currPositionX = playerX;
        this.currPositionY = playerY;
    }


    /**
     * Prompts player to take turn, and processes turn.
     * */
    public void takeTurn() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    /**
     * Sets a Player's role
     *
     * @param scene Scene in which the Role is being taken.
     * @param role Role to assign to the player.
     *
     * */
    public boolean takeRole(Scene scene, Role role)
    {
        if(role.roleRank > this.rank)
        {
            throw new RuntimeException("Player attempting to take role with higher rank!");
        }

        role.isOccupied = true;
        role.player = this;

        return true;
    }

    /**
     * Move the player to the target position on the board
     *
     * @param targetPosition Target position to move player to.
     * @param board Board in which player is being moved within.
     *
     * */
    public boolean move(Room targetPosition, BoardManager board) throws ExecutionControl.NotImplementedException
    {
        boolean canMove = board.checkDestination(this, targetPosition);

        if(!canMove)
        {
            throw new RuntimeException("Player attempting to move to invalid destination");
        }

        currentRoom = targetPosition;

        board.movePlayer(this, targetPosition);

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

    public Room getCurrentRoom()
    {
        return currentRoom;
    }
}
