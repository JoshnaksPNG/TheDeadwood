package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Scene;

import java.util.ArrayList;
import java.util.List;

public class SceneDeck
{
    private ArrayList<Scene> SceneCards;

    public SceneDeck(List<Scene> sceneList)
    {
        // Constructing new ArrayList from List Parameter
        SceneCards = new ArrayList<>(sceneList);
    }

    public Scene getScene()
    {
        int random = (int) (Math.random() * SceneCards.size());
        Scene scene = SceneCards.get(random);
        SceneCards.remove(random);
        return scene;
    }
}
