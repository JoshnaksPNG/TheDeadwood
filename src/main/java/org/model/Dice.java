package org.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Dice
{
    public static int result;

    public static int[] results;

    private static Random _random;

    private static final int _DICELOW = 1;

    private static final int _DICEHIGH = 6;

    private static Dice die = new Dice();

    Dice()
    {
        long seed = new Date().getTime();
        _random = new Random(seed);

        results = new int[0];
    }

    public static int roll()
    {
        int res = _random.nextInt(_DICELOW, _DICEHIGH + 1);

        result = res;

        return res;
    }

    public static int roll(int practiceChips) {
        result = 1 + (int) (6 * Math.random());
        return result + practiceChips;
    }

    int[] rollMulti(int rollCount) throws ExecutionControl.NotImplementedException
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
}
