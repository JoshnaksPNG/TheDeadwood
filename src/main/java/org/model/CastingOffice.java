package org.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class CastingOffice extends Room
{
    int[][] upgradeCosts;

    CastingOffice()
    {}

    // I'd probably use an enum to distinguish currency instead
    int getCost(int rank, char currency) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    boolean upgradeRank(Player player, int rank, char currency) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void upgradeMoney(Player player, int rank) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void upgradeCredit(Player player, int rank) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
