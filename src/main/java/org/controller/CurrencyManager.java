package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;

public class CurrencyManager
{
    private static CurrencyManager currencyManager = new CurrencyManager();

    CurrencyManager() {
    }

    public static CurrencyManager getCurrencyManager() {
        return currencyManager;
    }

    public static int checkPlayerMoney(Player player)
    {
        return player.getMoney();
    }

    public static int checkPlayerCredit(Player player)
    {
        return player.getCredit();
    }

    /**
     * Updates the Player's currency, specifically dollars here
     * 
     * For pay, the integer num is positive
     * For upgrading, the integer num is negative
     * 
     * @param player Target player for changes
     * @param num value to change currency by. Positive for increasing money, negative for decreasing money
     */
    public static void updatePlayerMoney(Player player, int num)
    {
        int currentMoney = player.getMoney();
        player.setMoney(currentMoney + num);

        System.INSTANCE.getView().PlayerPaid(player, num, true);
    }

    /**
     * Updates the Player's currency, specifically dollars here
     * 
     * For pay, the integer num is positive
     * For upgrading, the integer num is negative
     * 
     * @param player Target player for changes
     * @param num value to change currency by. Positive for increasing money, negative for decreasing money
     */
    public static void updatePlayerCredit(Player player, int num)
    {
        int currentCredit = player.getCredit();
        player.setCredit(currentCredit + num);

        System.INSTANCE.getView().PlayerPaid(player, num, false);
    }
}
