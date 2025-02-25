package org.view;

import org.model.Player;
import org.model.Role;
import org.model.Room;

public interface IView
{
    void AddPlayer(Player player, Room room);

    void PostPlayerMove(Player player, Room room);

    void PlayerTakeRole(Player player, Role role);

    void PlayerReleaseRole(Player player);

    void DisplayPlayerCurrency(Player player);

    void PromptPlayerTurnAction(Player player);

    int PromptPlayerAmount();

    void EndGame();
}
