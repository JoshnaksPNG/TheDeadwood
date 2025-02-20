package org.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

import org.controller.CurrencyManager;

public class CastingOffice extends Room
{
    private static CastingOffice castingOffice = new CastingOffice();
    
    private static int[][] upgradeCosts = {
        {4, 5},    // rank 2
        {10, 10},  // rank 3
        {18, 15},  // rank 4
        {28, 20},  // rank 5
        {40, 25}   // rank 6
    };

    CastingOffice()
    {}

    public static CastingOffice getCastingOffice() {
        return castingOffice;
    }

    // I'd probably use an enum to distinguish currency instead
    public static int getCost(int rank, boolean money) {
        if (money) {
            return upgradeCosts[rank - 2][0];
        } else {
            return upgradeCosts[rank - 2][1];
        }
    }

    public static boolean upgrade(Player player, int rank, boolean upgradeWithMoney) {
        if (upgradeWithMoney) {
            if(player.getMoney() >= getCost(rank, upgradeWithMoney)) {
                upgradeMoney(player, rank);
            } else {
                return false;
            }
        } else {
            if(player.getCredit() >= getCost(rank, upgradeWithMoney)) {
                upgradeCredits(player, rank);
            } else {
                return false;
            }
        }
        return true;
    }

    public static void upgradeMoney(Player player, int rank) {
        player.setRank(rank);
        CurrencyManager.updatePlayerMoney(player, -getCost(rank, true));
    }

    public static void upgradeCredits(Player player, int rank) {
        player.setRank(rank);
        CurrencyManager.updatePlayerCredit(player, -getCost(rank, false));
    }
}
