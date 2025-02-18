package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;

public class CurrencyManager
{
    int checkPlayerDollar(Player player)
    {
        return player.money;
    }

    int checkPlayerCredit(Player player)
    {
        return player.credit;
    }

    void updatePlayerDollar(Player player, int dollars)
    {
        player.money = dollars;
    }

    void updatePlayerCredit(Player player, int credit)
    {
        player.credit = credit;
    }
}
