package org.model;

import org.controller.BoardManager;
import org.controller.SceneManager;
import org.controller.System;

public class Player
{
    private int playerNumber;
    private int rank;
    private int money;
    private int credit;
    private Role role = null;
    private boolean isInRole;
    private int practiceChips;

    private boolean canUpgrade = false;
    private boolean canMove = true;
    private boolean canAct = false;
    private boolean canRehearse = false;
    private String iconName;
    private int iconIndex;

    private Room currentRoom;

    public Player(int playerNumber, int rank, int money, int credit, Room startingRoom)
    {
        this.playerNumber = playerNumber;
        this.rank = rank;
        this.money = money;
        this.credit = credit;
        currentRoom = startingRoom;

        practiceChips = 0;
    }


    /**
     * Prompts player to take turn, and processes turn.
     * */
    public void takeTurn(System.TurnDetails details)
    {
        switch (details.TurnType)
        {
            case Act ->
            {
                SceneManager.Instance.actScene(this, ((Set) currentRoom)._Scene);
            }

            case Rehearse ->
            {
                SceneManager.Instance.rehearseScene(this, ((Set) currentRoom)._Scene);
            }

            case Upgrade ->
            {
                CastingOffice.upgrade(this, rank + 1, details.CurrencyType.equals(System.TurnDetails.UpgradeCurrency.Money));
            }

            case TakeRole ->
            {
                SceneManager.Instance.updateRole(this, null, details.TakenRole);
                //takeRole(null, details.TakenRole);
            }

            case Move ->
            {
                move(details.MoveDest, BoardManager.Instance);
                System.INSTANCE.getView().PostPlayerMove(this, currentRoom);
            }
        }
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
        if(!SceneManager.Instance.verifyRoleRequirement(this, scene, role))
        {
            throw new RuntimeException("Player attempting to take role with higher rank!");
        }

        System.INSTANCE.getView().PlayerTakeRole(this, role);

        role.setActor(this);

        this.role = role;

        isInRole = true;

        practiceChips = 0;

        return true;
    }

    /**
     * Move the player to the target position on the board
     *
     * @param targetPosition Target position to move player to.
     * @param board Board in which player is being moved within.
     *
     * */
    public boolean move(Room targetPosition, BoardManager board)
    {
        boolean canMove = board.checkDestination(this, targetPosition);

        if(!canMove)
        {
            throw new RuntimeException("Player attempting to move to invalid destination");
        }

        currentRoom = targetPosition;

        board.forceMovePlayer(this, targetPosition);

        return canMove;
    }

    public void ResetAct()
    {
        System.INSTANCE.getView().PlayerReleaseRole(this);

        practiceChips = 0;
        role = null;
        isInRole = false;
    }

    // Getter and Setter for playerNumber
    public int getPlayerNumber() {
        return playerNumber;
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

    // Getter and Setter for isInRole
    public boolean isInRole() {
        return isInRole;
    }

    // Getter and Setter for practiceChips
    public int getPracticeChips() {
        return practiceChips;
    }

    public void setPracticeChips(int practiceChips) {
        this.practiceChips = practiceChips;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void ForceSetRoom(Room room)
    {
        currentRoom = room;
    }
}
