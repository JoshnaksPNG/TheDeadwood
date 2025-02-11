package org.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class Scene extends Room
{
    int budget;

    boolean _isFaceUp;

    ArrayList<Role> roles;

    String description;

    int shotsLeft;

    Scene(int budget, ArrayList<Role> roles)
    {
        this.budget = budget;
        this.roles = roles;
    }

    boolean IsFaceUp()
    {
        return _isFaceUp;
    }

    void flipCard() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
