package org.model;

import jdk.jshell.spi.ExecutionControl;

public class Dice
{
    int result;

    int[] results;

    Dice()
    {}

    int roll() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    int[] rollMulti(int rollCount) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
