package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;
import org.model.Room;

public class BoardManager
{
    Room[][] rooms;

    /**
     * Checks to see if the target destination is a valid position
     * for the player to move to.
     *
     * @param player Player for which to check move validity
     * @param x Target X position to check move validity
     * @param y Target Y position to check more validity
     *
     * */
    public boolean checkDestination(Player player, int x, int y) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    /**
     *
     *
     * */
    public void adjustBoard(int layout) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    /**
     * Initialize a board
     * */
    public void initializeBoard() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }


    /**
     * Move a player to target position.
     *
     * @param player Player which will move
     * @param targetX Target X of which to move player to
     * @param targetY Target Y of which to move player to
     * */
    public void movePlayer(Player player, int targetX, int targetY) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
