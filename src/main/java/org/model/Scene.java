package org.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class Scene extends Room
{
    public int budget;

    public boolean _isFaceUp;

    public ArrayList<Role> roles;

    public String description;

    public int shotsLeft;

    public Scene(int budget, ArrayList<Role> roles)
    {
        this.budget = budget;
        this.roles = roles;
    }

    public boolean IsFaceUp()
    {
        return _isFaceUp;
    }

    public void flipCard()
    {
        _isFaceUp = !_isFaceUp;
    }
}
