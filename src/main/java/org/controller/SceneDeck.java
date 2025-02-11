package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneDeck
{
    ArrayList<Scene> SceneCards;

    SceneDeck(List<Scene> sceneList)
    {
        // Constructing new ArrayList from List Parameter

        SceneCards = new ArrayList<>(sceneList);
    }

    Scene getScene() throws ExecutionControl.NotImplementedException
    {
        throw new ExecutionControl.NotImplementedException("Method Not Implemented");
    }
}
