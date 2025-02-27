package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;
import org.model.Role;
import org.model.Scene;
import java.util.ArrayList;
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
    }

    boolean verifyRoleRequirement(Player player, Scene scene, Role role) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void updateRole(Player player, Scene scene, Role role) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }

    void rehearseScene(Player player, Scene scene) throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
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
