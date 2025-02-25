package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;
import org.model.Role;
import org.model.Room;
import org.view.IView;

import java.util.ArrayList;

public class System
{
    IView _View;

    int days;

    int currDay;

    ArrayList<Player> players;

    BoardManager board;

    SceneManager sceneManager;

    int currTurn;

    public System(IView view)
    {
        _View = view;
        players = new ArrayList<>();
    }

    public void initializePlayers(int numPlayers)
    {
        int playerCount = _View.PromptPlayerAmount();

        for(int i = 0; i < playerCount; ++i)
        {
            Player p = new Player(i + 1, 1, 0, 0, 0, 0);

            players.add(p);

            // Replace the new Room() here
            _View.AddPlayer(p, new Room() {});
        }
    }

    public void setupBoard(BoardManager board) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public void updateRole(Role role) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public boolean checkEndOfGame() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public ArrayList<Player> determinePlayerOrder() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public void endDay() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public void endGame() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public void manageTurns() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    public void movePlayerPosition(Player player, int targetX, int targetY) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
