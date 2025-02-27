package org.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Dice
{
    public int result;

    public int[] results;

    private Random _random;

    private static final int _DICELOW = 1;

    private static final int _DICEHIGH = 6;

    public Dice()
    {
        long seed = new Date().getTime();
        _random = new Random(seed);

        results = new int[0];
    }

    public int roll()
    {
        int res = _random.nextInt(_DICELOW, _DICEHIGH + 1);

        result = res;

        return res;
    }

    public int BoostedRoll(int boost)
    {
        return roll() + boost;
    }

    public int[] rollMulti(int rollCount) throws ExecutionControl.NotImplementedException
    {
        results = new int[rollCount];

        ArrayList<Integer> res = new ArrayList<>();

        for(int i = 0; i < rollCount; ++i)
        {
            res.add(roll());
        }

        for(int i = 0; i < rollCount; ++i)
        {
            results[i] = res.get(i);
        }

        return results.clone();
    }

    public static Dice Instance = new Dice();
}
