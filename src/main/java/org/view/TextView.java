package org.view;

import org.controller.BoardManager;
import org.controller.SceneManager;
import org.controller.System;
import org.controller.System.TurnDetails;
import org.model.*;

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
                + " moved to room: " + room.GetName());
    }

    @Override
    public void PlayerTakeRole(Player player, Role role)
    {

        _OutStream.println("Player " + player.getPlayerNumber()
                + " took role: " + role.getName());
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
    public void PlayerPaid(Player player, int amount, boolean isMoney)
    {
        int currencyTotal = 0;
        String currencyType = "";

        if(isMoney)
        {
            currencyTotal = player.getMoney();
            currencyType = "dollars";
        } else
        {
            currencyTotal = player.getCredit();
            currencyType = "credits";
        }

        _OutStream.println("Player " + player.getPlayerNumber() +
                " was paid an amount of " + amount + " " + currencyType +
                ". They have a total of " + currencyTotal + " " + currencyType);
    }

    @Override
    public System.TurnDetails PromptPlayerTurnAction(Player player) {

        boolean isWorkValid = player.isInRole();

        boolean isUpgradeValid =
                (player.getCurrentRoom() instanceof CastingOffice) &&
                (CastingOffice.CanUpgradePlayer(player));

        _OutStream.println( "\n\n\nPlayer " + player.getPlayerNumber() +
                ", which action do you wish to take?");

        if(isWorkValid)
        {
            _OutStream.println("W: work");
        } else
        {
            _OutStream.println("M: move");
        }

        if(isUpgradeValid)
        {
            _OutStream.println("U: upgrade");
        }

        boolean isValidChoice = false;

        String actionChoice = "";



        while (!isValidChoice)
        {
            String choice = _Scanner.nextLine().toLowerCase();

            isValidChoice = (choice.equals("u") && isUpgradeValid) ||
                            (choice.equals("m") && !isWorkValid) ||
                            (choice.equals("w") && isWorkValid);

            if(!isValidChoice)
            {
                _OutStream.println("Invalid input. Try again please...");
            }
            else
            {
                actionChoice = choice;
            }
        }

        System.TurnDetails details = switch (actionChoice) {
            case "m" -> PromptMove(player);
            case "u" -> PromptUpgrade(player);
            case "w" -> PromptWork(player);
            default -> null;
        };

        return details;
    }

    private System.TurnDetails PromptMove(Player player)
    {
        boolean canTakeRole = player.getCurrentRoom() instanceof Set &&
                ((Set)player.getCurrentRoom()).getScene() != null &&
                ((Set) player.getCurrentRoom()).HasAvailableRank(player.getRank());

        _OutStream.println("Which Move Action do you want to take?");
        _OutStream.println("M: move to another room");

        if(canTakeRole)
        {
            _OutStream.println("R: take a role");
        }

        String actionChoice = "";

        boolean isValidChoice = false;

        while (!isValidChoice)
        {
            String choice = _Scanner.nextLine().toLowerCase();

            isValidChoice = (choice.equals("m")) ||
                    (choice.equals("r") && canTakeRole);

            if(!isValidChoice)
            {
                _OutStream.println("Invalid input. Try again please...");
            }
            else
            {
                actionChoice = choice;
            }
        }

        TurnDetails details = switch (actionChoice) {
            case "m" -> PromptMoveRoom(player);
            case "r" -> PromptTakeRole(player);
            default -> null;
        };

        return details;
    }

    private System.TurnDetails PromptMoveRoom(Player player)
    {
        String chosenNeighbor = "";

        while (!player.getCurrentRoom().IsValidNeighborName(chosenNeighbor))
        {
            _OutStream.println("Select a room to move to:");

            for(String neigborName: player.getCurrentRoom().GetNeighborNames())
            {
                _OutStream.println(neigborName);
            }

            chosenNeighbor = _Scanner.nextLine();

            if(!player.getCurrentRoom().IsValidNeighborName(chosenNeighbor))
            {
                _OutStream.println("Please choose a valid room");
            }
        }

        Room chosenDestination = BoardManager.Instance.GetRoomByName(chosenNeighbor);

        return new System.TurnDetails(TurnDetails.ActionType.Move, chosenDestination);
    }

    private System.TurnDetails PromptTakeRole(Player player)
    {
        String chosenRName = "";

        while (!((Set) player.getCurrentRoom()).HasRoleName(chosenRName))
        {
            _OutStream.println("Select a role to take:");

            for(Role role: ((Set) player.getCurrentRoom()).GetAvailableRoles())
            {
                if(SceneManager.Instance.verifyRoleRequirement(player, null, role))
                {
                    _OutStream.println(role.getName());
                }

            }

            chosenRName = _Scanner.nextLine();

            if(!((Set) player.getCurrentRoom()).HasRoleName(chosenRName))
            {
                _OutStream.println("Please choose a valid role...");
            }
        }

        Role chosenRole = ((Set) player.getCurrentRoom()).GetRoleByName(chosenRName);

        return new TurnDetails(TurnDetails.ActionType.TakeRole, chosenRole);
    }

    private System.TurnDetails PromptUpgrade(Player player)
    {
        _OutStream.println("How do you want to upgrade your rank?");

        boolean isMoneyValid = player.getMoney() >= CastingOffice.getCost(player.getRank() + 1, true);
        boolean isCreditsValid = player.getCredit() >= CastingOffice.getCost(player.getRank() + 1, false);

        if(isMoneyValid)
        {
            _OutStream.println("M: money");
        }

        if(isCreditsValid)
        {
            _OutStream.println("C: credits");
        }

        boolean isValidChoice = false;

        String actionChoice = "";

        while (!isValidChoice)
        {
            String choice = _Scanner.nextLine().toLowerCase();

            isValidChoice = (choice.equals("m") && isMoneyValid) ||
                    (choice.equals("c") && isCreditsValid);

            if(!isValidChoice)
            {
                _OutStream.println("Invalid input. Try again please...");
            }
            else
            {
                actionChoice = choice;
            }
        }

        TurnDetails.UpgradeCurrency curr = switch (actionChoice) {
            case "m" -> TurnDetails.UpgradeCurrency.Money;
            case "c" -> TurnDetails.UpgradeCurrency.Credits;
            default -> null;
        };

        return new TurnDetails(TurnDetails.ActionType.Upgrade, curr);
    }

    private System.TurnDetails PromptWork(Player player)
    {
        boolean canRehearse = player.getPracticeChips() <
                ((Set)player.getCurrentRoom()).getScene().getBudget() - 1;

        _OutStream.println("Which kind of work do you want to do?");
        _OutStream.println("A: act");

        if(canRehearse)
        {
            _OutStream.println("R: rehearse");
        }


        String actionChoice = "";

        boolean isValidChoice = false;

        while (!isValidChoice)
        {
            String choice = _Scanner.nextLine().toLowerCase();

            isValidChoice = (choice.equals("r") && canRehearse) ||
                    (choice.equals("a"));

            if(!isValidChoice)
            {
                _OutStream.println("Invalid input. Try again please...");
            }
            else
            {
                actionChoice = choice;
            }
        }

        TurnDetails.ActionType workType = switch (actionChoice) {
            case "r" -> TurnDetails.ActionType.Rehearse;
            case "a" -> TurnDetails.ActionType.Act;
            default -> null;
        };

        return new TurnDetails(workType);
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
    public void EndDay(int day)
    {
        _OutStream.println("Ending of Day...\nMoving all players back to trailer...");

        for (Player p: System.INSTANCE.getPlayers())
        {
            DisplayPlayerCurrency(p);
        }
    }

    @Override
    public void BeginDay(int DayNumber)
    {
        _OutStream.println("Starting Day " + DayNumber);
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

    @Override
    public void PostPlayerAct(Player player, Role role, boolean isSuccess)
    {
        if (isSuccess)
        {
            _OutStream.println("\n\"" + role.getLine() + "\"");

            _OutStream.println("Player " + player.getPlayerNumber() +
                    " successfully acted out their role: " + role.getName() + ".\n" +
                    "A shot has been finished, and they have received payment.");
        } else
        {
            _OutStream.println("Player " + player.getPlayerNumber() +
                    " failed to act out their role: " + role.getName() + ".");
        }
    }

    @Override
    public void SceneWrappedOnSet(Set set)
    {
        _OutStream.println("\nFilming of " + set.getScene().getName() +
                " has wrapped on " + set.GetName() +
                ". All Actors have been paid and released from their roles.");
    }
}
