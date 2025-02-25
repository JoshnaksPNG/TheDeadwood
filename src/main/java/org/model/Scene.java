package org.model;

import jdk.jshell.spi.ExecutionControl;
import java.util.ArrayList;

public class Scene extends Room {
    private int num; // scene card number
    private String name;
    private int budget;
    private String description;
    private boolean _isFaceUp;
    private ArrayList<Role> roles;
    private int shotsLeft;

    public Scene(int num, String name, String description, int budget, ArrayList<Role> roles) {
        this.num = num;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.roles = roles;
        this._isFaceUp = false; // starts faced down
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBudget() {
        return budget;
    }

    public ArrayList<Role> getRoles() {
        return roles;
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
