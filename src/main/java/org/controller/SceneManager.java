package org.controller;

import jdk.jshell.spi.ExecutionControl;

import org.model.CastingOffice;
import org.model.Dice;
import org.model.Player;
import org.model.Role;
import org.model.Room;
import org.model.Scene;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SceneManager
{
    int activeScenes;

    ArrayList<Scene> currScenes;

    public SceneManager(List<Scene> scenes)
    {
        int activeScenes = 0;

        currScenes = new ArrayList<>();

        for(Scene s: scenes)
        {
            currScenes.add(s);
        }

        Instance = this;
    }

    boolean verifyRoleRequirement(Player player, Scene scene, Role role)
    {
        if(player.getRank() >= role.getRank()) {
            return true;
        } else {
            return false;
        }
    }

    void updateRole(Player player, Scene scene, Role role)
    {
        player.setRole(role);
        role.setActor(player);
    }

    boolean rehearseScene(Player player, Scene scene)
    {
        if (player.getPracticeChips() >= scene.getBudget() - 1) {
            return false; // Player must act, they have the max amount of practice chips
        } else {
            int currentChips = player.getPracticeChips();
            player.setPracticeChips(currentChips + 1);
            return true;
        }
    }

    boolean actScene(Player player, Scene scene) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void wrapScene(Scene scene) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
