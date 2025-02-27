package org.view;

import org.controller.System;
import org.model.Player;
import org.model.Role;
import org.model.Room;

import java.sql.Struct;

public interface IView
{
    void AddPlayer(Player player, Room room);

    void PostPlayerMove(Player player, Room room);

    void PlayerTakeRole(Player player, Role role);

    void PlayerReleaseRole(Player player);

    void DisplayPlayerCurrency(Player player);

    System.TurnDetails PromptPlayerTurnAction(Player player);

    int PromptPlayerAmount();

    void EndDay();

    void BeginDay(int DayNumber);

    void EndGame();

}
