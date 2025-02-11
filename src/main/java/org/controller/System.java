package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;
import org.model.Role;

import java.util.ArrayList;

public class System
{
    int days;

    int currDay;

    ArrayList<Player> players;

    BoardManager board;

    SceneManager sceneManager;

    int currTurn;

    void initializePlayers(int numPlayers) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void setupBoard(BoardManager board) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void updateRole(Role role) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    boolean checkEndOfGame() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    ArrayList<Player> determinePlayerOrder() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void endDay() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void endGame() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void manageTurns() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void movePlayerPosition(Player player, int targetX, int targetY) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
