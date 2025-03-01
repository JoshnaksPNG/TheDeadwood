package org.runtime;

import org.controller.BoardManager;
import org.controller.SceneManager;
import org.controller.System;
import org.model.CastingOffice;
import org.model.Room;
import org.model.Scene;
import org.view.TextView;
import org.w3c.dom.Document;
import org.xml.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        java.lang.System.out.println("Loading Data>>>>>>>>>>\n\n\n");

        Room[] loadedRooms;

        ArrayList<Scene> loadedCards;

        try
        {
            Document boardDoc = XMLParser.getDocFromFile("src/main/java/org/xml/board.xml");
            Document cardsDoc = XMLParser.getDocFromFile("src/main/java/org/xml/cards.xml");

            loadedRooms = XMLParser.readBoardData(boardDoc);
            loadedCards = XMLParser.readCardData(cardsDoc);

        } catch (ParserConfigurationException e)
        {
            throw new RuntimeException(e);
        }

        ArrayList<Room> AllRooms = new ArrayList<>();

        new SceneManager(loadedCards);

        new BoardManager(SceneManager.Instance);

        for (Room r: loadedRooms)
        {
            BoardManager.Instance.AddRoom(r);

            if(r instanceof CastingOffice)
            {
                BoardManager.Instance.SetOffice((CastingOffice) r);
            }

            if(r.GetName() == "trailer")
            {
                BoardManager.Instance.SetTrailer(r);
            }
        }

        TextView textView = new TextView(java.lang.System.in, java.lang.System.out);

        java.lang.System.out.println("\n\n\nInitializing Game...");

        org.controller.System sys = new System(textView);

        sys.setupBoard(BoardManager.Instance);

        sys.initializePlayers(0);

        sys.manageTurns();
    }
}