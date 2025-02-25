package org.view;

import org.model.Player;
import org.model.Role;
import org.model.Room;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TextView implements IView
{
    InputStream _InStream;
    Scanner _Scanner;

    PrintStream _OutStream;

    ArrayList<Player> _AllPlayers;

    public TextView(InputStream iStream, PrintStream oStream)
    {
        _InStream = iStream;
        _OutStream = oStream;

        _Scanner = new Scanner(iStream);
        _AllPlayers = new ArrayList<>();
    }

    @Override
    public void AddPlayer(Player player, Room room)
    {
        _AllPlayers.add(player);
        _OutStream.println("Added Player: " + player.getPlayerNumber());
    }

    @Override
    public void PostPlayerMove(Player player, Room room)
    {
        _OutStream.println("Player " + player.getPlayerNumber()
                + " moved to room: " + room.toString());
    }

    @Override
    public void PlayerTakeRole(Player player, Role role)
    {

        _OutStream.println("Player " + player.getPlayerNumber()
                + " took role: " + role.toString());
    }

    @Override
    public void PlayerReleaseRole(Player player)
    {
        _OutStream.println("Player " + player.getPlayerNumber()
                + " completed their role.");
    }

    @Override
    public void DisplayPlayerCurrency(Player player)
    {
        _OutStream.println("Player " + player.getPlayerNumber() +
                " has " + player.getMoney() + " money, and " +
                player.getCredit() + "credits.");
    }

    @Override
    public void PromptPlayerTurnAction(Player player) {
        _OutStream.println( "Player " + player.getPlayerNumber() +
                ", which action do you wish to take?\nM: move,\nW: work");
        // If Player is on casting office, offer upgrade
        _OutStream.println("U: upgrade");

        boolean isValidChoice = false;

        String actionChoice = "";

        while (!isValidChoice)
        {
            String choice = _Scanner.nextLine().toLowerCase();

            isValidChoice = choice.equals("u") || choice.equals("m") || choice.equals("w");

            if(!isValidChoice)
            {
                _OutStream.println("Invalid input. Try again please...");
            }
            else
            {
                actionChoice = choice;
            }
        }

        switch (actionChoice)
        {
            case "m":
                break;

            case "u":
                break;

            case "w":
                break;
        }
    }

    @Override
    public int PromptPlayerAmount()
    {
        _OutStream.println("How many players are playing? Between 2-8");

        int playerCount = -1;

        while (playerCount > 8 || playerCount < 2)
        {
            playerCount = _Scanner.nextInt();

            if(playerCount > 8 || playerCount < 2)
            {
                _OutStream.println("Please enter an integer between 2 and 8...");
            }
        }

        return playerCount;
    }

    @Override
    public void EndGame()
    {
        Player topDog = null;

        int topScore = -1;

        for (Player p: _AllPlayers)
        {
            int score = p.getCredit() + p.getMoney() + (p.getRank() * 5);

            _OutStream.println("Player " + p.getPlayerNumber() + " scored: "
                    + score);

            if (score > topScore)
            {
                topDog = p;
                topScore = score;
            }
        }

        if(topDog != null)
        {
            _OutStream.println("Player " + topDog.getPlayerNumber() + " Wins!");
        }
    }
}
