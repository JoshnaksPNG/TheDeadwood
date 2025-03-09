package org.controller;

import jdk.jshell.spi.ExecutionControl;
import org.model.Player;
import org.model.Role;
import org.model.Room;
import org.model.Set;
import org.view.IView;

import java.util.ArrayList;
import java.util.Random;

public class System
{
    IView _View;
    int days;
    int currDay = 1;
    ArrayList<Player> players;
    BoardManager _Board;
    SceneManager sceneManager;

    // Should start at 0;
    int currTurn;

    public System(IView view) {
        _View = view;
        players = new ArrayList<>();

        INSTANCE = this;
    }

    public void initializePlayers(int numPlayers) {
        int playerCount = _View.PromptPlayerAmount();

        if (playerCount <= 3){
            days = 3;
        } else {
            days = 4;
        }

        for (int i = 0; i < playerCount; ++i) {
            Player p = new Player(i + 1, 1, 0, 0, _Board._Trailer);
            players.add(p);

            if(playerCount == 5) {
                CurrencyManager.updatePlayerCredit(p, 2);
            }

            if(playerCount == 6) {
                CurrencyManager.updatePlayerCredit(p, 4);
            }

            if(playerCount >= 7) {
                p.setRank(2);
            }

            // Replace the new Room() here
            _View.AddPlayer(p, _Board._Trailer);
        }
    }

    public void setupBoard(BoardManager board) {
        _Board = board;
        _Board.initializeBoard();
        sceneManager = _Board.GetSceneManager();
    }

    public ArrayList<Player> determinePlayerOrder() {
        ArrayList<Player> NewPlayerOrder = new ArrayList<>(players.size());
        Random r = new Random();

        while (players.size() > 0) {
            Player p = players.get(r.nextInt(players.size()));
            NewPlayerOrder.add(p);
        }
        players = NewPlayerOrder;
        return players;
    }

    public void endDay() {
        _View.EndDay(currDay);

        ++currDay;
        currTurn = 0;

        if(currDay >= days) {
            endGame();
        } else {
            for (Player p: players) {
                movePlayerPosition(p, _Board.GetTrailer());
                _View.PostPlayerMove(p, _Board.GetTrailer());

                p.ResetAct();
            }

            for(Room r: BoardManager.Instance._AllRooms) {
                if(r instanceof Set)
                {
                    ((Set) r).RequestNewScene();
                }
            }
        }
    }

    public void endGame() {
        _View.EndGame();
    }

    public void manageTurns() {
        while (currDay < days)
        {
            playDay();
        }

    }

    public void playDay() {
        boolean AvailableScenes = true;

        _View.BeginDay(currDay);

        while (AvailableScenes) {
            for(Player p: players) {
                p.takeTurn(_View.PromptPlayerTurnAction(p));

                AvailableScenes = !SceneManager.Instance.AreScenesWrapped();
                if (!AvailableScenes) {
                    break;
                }
            }
        }

        endDay();
    }

    public void movePlayerPosition(Player player, Room targetRoom) {
        _Board.forceMovePlayer(player, targetRoom);
    }

    public IView getView () {
        return _View;
    }

    public static class TurnDetails {
        public enum ActionType {
            Move,
            TakeRole,
            Upgrade,
            Work,
            Act,
            Rehearse,
        }

        public enum UpgradeCurrency {
            Money,
            Credits,
        }

        public TurnDetails(ActionType a, Room r) {
            TurnType = a;

            MoveDest = r;
        }

        public TurnDetails(ActionType a, UpgradeCurrency u) {
            TurnType = a;

            CurrencyType = u;
        }

        public TurnDetails(ActionType a) {
            TurnType = a;
        }

        public TurnDetails(ActionType a, Role r) {
            TurnType = a;
            TakenRole = r;
        }

        public ActionType TurnType;

        public Room MoveDest;

        public UpgradeCurrency CurrencyType;

        public Role TakenRole;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>(this.players.size());

        for(Player p : this.players) {
            players.add(p);
        }

        return players;
    }

    public static System INSTANCE;
}
